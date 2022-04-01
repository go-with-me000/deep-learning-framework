from django.shortcuts import render
from django.core import serializers
from django.http import HttpResponse, JsonResponse
from django.db import models
import json
import os
import torch
from torchvision import transforms
from torch import  nn,optim
from PIL import  Image
from torchvision.models import resnet18
from .utils import Flatten
from django.http import HttpResponse, JsonResponse
import sys

# Create your views here.

# img_path="pika.jpg"
# img_path="jpg/"+ str(sys.argv[1])
abs_path = os.getcwd()
abs_path = abs_path+'/myapp/'
img_path = abs_path+"jpg/"
img_const_path = img_path
assert os.path.exists(img_path), "files:{} does not exist".format(img_path)
# img = Image.open(img_path)
# plt.imshow(img)
# # plt.show()
# img = tf(img)
# print(img)
device=torch.device("cuda")
def load_list(root):
    name2label = {}
    for name in sorted(os.listdir(os.path.join(root))):
        if not os.path.isdir(os.path.join(root, name)):
            continue
        name2label[name] = len(name2label.keys())
    print(name2label)
    return name2label

def main(request,pic, dataset, model_name):
    # root = "dataset/"+str(sys.argv[2])
    global img_path
    img_path = img_const_path+pic
    root = abs_path+"dataset/"+dataset
    name2label = load_list(root)
    lens = len(name2label)
    train_model = resnet18(pretrained=True)
    model = nn.Sequential(*list(train_model.children())[:-1],
                          Flatten(),
                          nn.Linear(512, lens),
                          ).to(device)
    model_name = abs_path+'mdl/'+model_name
    model.load_state_dict(torch.load(model_name))
    model.eval()

    tf = transforms.Compose([
        lambda x: Image.open(x).convert("RGB"),
        transforms.Resize((int(224 * 1.25), int(224 * 1.25))),
        transforms.RandomRotation(15),
        transforms.CenterCrop(224),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406],
                             std=[0.229, 0.224, 0.225])
    ])
    img = tf(img_path)

    image = torch.reshape(img, (1, 3, 224, 224))

    with torch.no_grad():
        image = image.to(device)
        out = model(image)
        _, predicted = torch.max(out, 1)
        result = predicted.item()

    output=""
    for key,value in name2label.items():
        if result==value:
            output=key
            break
    print(output)
    data ={
        'output':output
    }
    return JsonResponse(data)
