import os
import sys
from json.decoder import JSONArray

import torch
from torch.utils.data import DataLoader
from torch import nn, optim
from torchvision.models import resnet18
from .utils import Flatten
from .DataLoaders import DataLoaders
from django.http import HttpResponse, JsonResponse
from array import *
from .Lenet5 import Lenet5

batchsz = 32
lr = 1e-3
device = torch.device("cuda")
torch.manual_seed(1234)
myarray=array("l")
abs_path = os.getcwd()
abs_path = abs_path+'\\myapp\\'

# model_name ="mdl/"+ str(sys.argv[3])
# dataset_name ="dataset/"+ str(sys.argv[2])
# train_db = DataLoaders(dataset_name, 224, mode="train")
# val_db = DataLoaders(dataset_name, 224, mode="val")
# test_db = DataLoaders(dataset_name, 224, mode="test")
#
# train_loader = DataLoader(train_db, batch_size=batchsz, shuffle=True, num_workers=2)
# test_loader = DataLoader(test_db, batch_size=batchsz, num_workers=1)
# val_loader = DataLoader(val_db, batch_size=batchsz, num_workers=2)
model_name =abs_path+"mdl\\"
dataset_name =abs_path+"dataset\\"
model_const_name = model_name
dataset_const_name = dataset_name
train_db =""
val_db =""
test_db=""

train_loader = ""
test_loader = ""
val_loader = ""
isCuda = True
# viz = visdom.Visdom()
def evalute(model, loader):
    correct = 0
    total = len(loader.dataset)
    for x, y in loader:
        if isCuda == True:
            x, y = x.to(device), y.to(device)
        x.squeeze(0)
        # print("x:", x.shape)
        # print("y:", y.shape)
        with torch.no_grad():
            logits = model(x)
            # print("logits:",logits.shape)
            pred = logits.argmax(dim=1)

        correct += torch.eq(pred, y).sum().float().item()
    return correct / total


def train(model, isFirst,epochs):
    clearArray()
    if isFirst == 0:
        model.load_state_dict(torch.load(model_name))
    optimizer = optim.Adam(model.parameters(), lr=lr)
    criteon = nn.CrossEntropyLoss()
    best_acc, best_epoch = 0, 0
    for epoch in range(epochs):
        for step, (x, y) in enumerate(train_loader):
            if isCuda==True:
                x, y = x.to(device), y.to(device)
            # print("x:", x.shape)
            # print("y:", y.shape)
            logits = model.forward(x)
            # print("logits:", logits.shape)
            loss = criteon(logits, y)
            optimizer.zero_grad()
            loss.backward()
            optimizer.step()
        if epoch % 1 == 0:
            val_acc = evalute(model, val_loader)
            global myarray
            acc = int(val_acc *100)
            myarray.append(acc)
            if val_acc > best_acc:
                best_epoch = epoch
                best_acc = val_acc
                torch.save(model.state_dict(), model_name)
            print("epoch:",epoch, "   acc:",val_acc)
    print('best acc:', best_acc, 'best epoch:', best_epoch)
    data={
        'best_acc':best_acc,
        'best_epoch':best_epoch
    }
    return data

def test(model):
    model.load_state_dict(torch.load(model_name))

    print("loaded from skpt")
    test_acc = evalute(model, test_loader)
    print('test acc:', test_acc)
    data ={
        'test_acc':test_acc
    }
    return data
def clearArray():
    global myarray
    myarray=[]
def sendarray(request):
    global myarray
    number = list(myarray)
    data ={
        "key":number
    }
    return JsonResponse(data)

def load_list(root):
    name2label = {}
    for name in sorted(os.listdir(os.path.join(root))):
        if not os.path.isdir(os.path.join(root, name)):
            continue
        name2label[name] = len(name2label.keys())
    print(name2label)
    return name2label
def init(model_names, dataset_names):
    global model_name
    global dataset_name
    model_name = model_const_name+model_names
    dataset_name = dataset_const_name+dataset_names
    global train_db
    global val_db
    global test_db
    train_db = DataLoaders(dataset_names,dataset_name, 224, mode="train")
    val_db = DataLoaders(dataset_names,dataset_name, 224, mode="val")
    test_db = DataLoaders(dataset_names,dataset_name, 224, mode="test")

    global train_loader
    global test_loader
    global val_loader
    train_loader = DataLoader(train_db, batch_size=batchsz, shuffle=True, num_workers=2)
    test_loader = DataLoader(test_db, batch_size=batchsz, num_workers=1)
    val_loader = DataLoader(val_db, batch_size=batchsz, num_workers=2)

def main(request, isTrain, dataset_names, model_names, isFirst,epoch,network):
    global isCuda
    if network==1:
        isCuda=True
    else:
        isCuda=False

    init(model_names, dataset_names)
    # model = ResNet18(5).to(device)
    root =abs_path+ "dataset\\"+dataset_names
    name2label = load_list(root)
    lens = len(name2label)
    if network==1:
        train_model = resnet18(pretrained=True)
        model = nn.Sequential(*list(train_model.children())[:-1],
                              Flatten(),
                              nn.Linear(512, lens),
                              ).to(device)
    else:
        model = Lenet5(lens)
    data =""
    if isTrain == 1:
        data = train(model, isFirst,epoch)
    else:
        data = test(model)

    return JsonResponse(data)


