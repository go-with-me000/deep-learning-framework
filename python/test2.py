import os
import torch
from torchvision import transforms
from torch import  nn,optim
from PIL import  Image
from torchvision.models import resnet18
from utils import Flatten
import sys

# img_path="pika.jpg"
# img_path="jpg/"+ str(sys.argv[1])
img_path="jpg/pika.jpg"
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

def main():
    root = "dataset/pokeman"

    name2label = load_list(root)
    lens = len(name2label)
    train_model = resnet18(pretrained=True)
    model = nn.Sequential(*list(train_model.children())[:-1],
                          Flatten(),
                          nn.Linear(512, lens),
                          ).to(device)
    model_name = 'mdl/best2.mdl'
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

if __name__ == '__main__':
    main()
