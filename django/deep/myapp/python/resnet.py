import torch
from torch import  nn

from torch.nn import functional as F

from resblk import ResBlk
class ResNet18(nn.Module):
    def __init__(self,num_class):
        super(ResNet18,self).__init__()
        self.conv1 = nn.Sequential(
            nn.Conv2d(3,16,kernel_size=3,stride=3,padding=0),
            nn.BatchNorm2d(16)
        )
        self.blk1 = ResBlk(16,32,stride=3)
        self.blk2 = ResBlk(32,64,stride=3)
        self.blk3 = ResBlk(64,128,stride=2)
        self.blk4 = ResBlk(128,256,stride=2)

        self.outlayer = nn.Linear(256*3*3,num_class)   #输出是10维

    def forward(self, x):
        x = F.relu(self.conv1(x))
        x=self.blk1(x)
        x = self.blk2(x)
        x = self.blk3(x)

        x = self.blk4(x)

        # print("shape:",x.shape)

        # x=F.adaptive_avg_pool2d(x,[1,1])
        # print("after pool:",x.shape)
        x=x.view(x.size(0),-1)
        x = self.outlayer(x)

        return x


if __name__ == '__main__':
    blk = ResBlk(64,128,stride =4)
    tmp = torch.randn(2,64,224,224)
    out = blk(tmp)
    # print(out.shape)

    tmp = torch.randn(2,3,224,224)

    model = ResNet18(5)
    out = model(tmp)
    # print("resnet:",out.shape)