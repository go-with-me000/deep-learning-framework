import os

import torch
from torch.utils.data import DataLoader
from torchvision import datasets
from torchvision import transforms
# from lenet5 import Lenet5
from torch import  nn,optim
from PIL import  Image
from torch.nn import  functional as F
import torchvision
import visdom,time
import random,csv,glob
from torch.utils.data import  Dataset
from pokemen import Pokemon
from resnet import ResNet18

batchsz=32
lr=1e-3
epochs=50
device=torch.device("cuda")
torch.manual_seed(1234)

train_db = Pokemon('pokeman',224,mode="train")
val_db = Pokemon('pokeman',224,mode="val")
test_db = Pokemon('pokeman',224,mode="test")

train_loader = DataLoader(train_db,batch_size=batchsz,shuffle=True,num_workers=2)
test_loader=DataLoader(test_db,batch_size=2,num_workers=2)
val_loader = DataLoader(val_db,batch_size=batchsz,num_workers=2)
# viz = visdom.Visdom()
def evalute(model, loader):
    correct=0
    total = len(loader.dataset)
    for x,y in loader:
        # print(x.shape)
        # print(y.shape)
        x,y = x.to(device),y.to(device)
        x.squeeze(0)
        # print(x.shape)
        with torch.no_grad():
            logits = model(x)
            pred = logits.argmax(dim=1)
        correct+=torch.eq(pred,y).sum().float().item()
    return correct/total

def test():

    print("loaded from skpt")
    test_acc = evalute(model, test_loader)
    print('test acc:', test_acc)
if __name__ == '__main__':
    model = ResNet18(5).to(device)
    optimizer = optim.Adam(model.parameters(),lr=lr)
    criteon = nn.CrossEntropyLoss()
    best_acc,best_epoch=0,0
    # for epoch in range(epochs):
    #     for step, (x,y) in enumerate(train_loader):
    #         x,y = x.to(device),y.to(device)
    #         logits=model.forward(x)
    #         loss=criteon(logits,y)
    #         optimizer.zero_grad()
    #         loss.backward()
    #         optimizer.step()
    #     if epoch %1==0:
    #         val_acc =evalute(model,val_loader)
    #         if val_acc >best_acc:
    #             best_epoch = epoch
    #             best_acc = val_acc
    #             torch.save(model.state_dict(),'best.mdl')
    #             print(111)
    # print('best acc:', best_acc, 'best epoch:', best_epoch)
    model.load_state_dict(torch.load('best.mdl'))

    print("loaded from skpt")
    test_acc = evalute(model,test_loader)
    print('test acc:', test_acc)
    # pass