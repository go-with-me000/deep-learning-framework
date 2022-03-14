import os
import sys
import torch
from torch.utils.data import DataLoader
from torch import nn, optim
from pokemen import Pokemon
from torchvision.models import resnet18
from utils import Flatten
from DataLoaders import DataLoaders

batchsz = 32
lr = 1e-3
epochs = 10
device = torch.device("cuda")
torch.manual_seed(1234)
model_name ="mdl/"+ str(sys.argv[3])
dataset_name ="dataset/"+ str(sys.argv[2])
train_db = DataLoaders(dataset_name, 224, mode="train")
val_db = DataLoaders(dataset_name, 224, mode="val")
test_db = DataLoaders(dataset_name, 224, mode="test")

train_loader = DataLoader(train_db, batch_size=batchsz, shuffle=True, num_workers=2)
test_loader = DataLoader(test_db, batch_size=batchsz, num_workers=1)
val_loader = DataLoader(val_db, batch_size=batchsz, num_workers=2)


# viz = visdom.Visdom()
def evalute(model, loader):
    correct = 0
    total = len(loader.dataset)
    for x, y in loader:
        x, y = x.to(device), y.to(device)
        x.squeeze(0)

        with torch.no_grad():
            logits = model(x)
            pred = logits.argmax(dim=1)

        correct += torch.eq(pred, y).sum().float().item()
    return correct / total


def train(model):
    optimizer = optim.Adam(model.parameters(), lr=lr)
    criteon = nn.CrossEntropyLoss()
    best_acc, best_epoch = 0, 0
    for epoch in range(epochs):
        for step, (x, y) in enumerate(train_loader):
            x, y = x.to(device), y.to(device)
            logits = model.forward(x)
            loss = criteon(logits, y)
            optimizer.zero_grad()
            loss.backward()
            optimizer.step()
        if epoch % 2 == 0:
            val_acc = evalute(model, val_loader)
            if val_acc > best_acc:
                best_epoch = epoch
                best_acc = val_acc
                torch.save(model.state_dict(), model_name)
                print(111)
    print('best acc:', best_acc, 'best epoch:', best_epoch)


def test(model):
    model.load_state_dict(torch.load(model_name))

    print("loaded from skpt")
    test_acc = evalute(model, test_loader)
    print('test acc:', test_acc)


def load_list(root):
    name2label = {}
    for name in sorted(os.listdir(os.path.join(root))):
        if not os.path.isdir(os.path.join(root, name)):
            continue
        name2label[name] = len(name2label.keys())
    print(name2label)
    return name2label


if __name__ == '__main__':
    # model = ResNet18(5).to(device)
    root ="dataset/"+ str(sys.argv[2])
    name2label = load_list(root)
    lens = len(name2label)

    train_model = resnet18(pretrained=True)
    model = nn.Sequential(*list(train_model.children())[:-1],
                          Flatten(),
                          nn.Linear(512, lens),
                          ).to(device)
    isTrain = int(sys.argv[1])
    if isTrain == 1:
        train(model)
    else:
        test(model)
