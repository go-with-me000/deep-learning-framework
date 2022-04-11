import torch
from torch import  nn

from torch.nn import functional as F

device = torch.device("cuda")
class Lenet5(nn.Module):
    def __init__(self,lens):
        super(Lenet5,self).__init__()
        self.lens = lens
        self.conv_unit = nn.Sequential(
            nn.Conv2d(3,6,kernel_size=5,stride=1,padding=0),
            nn.AvgPool2d(kernel_size=2,stride=2,padding=0),
            nn.Conv2d(6,16,kernel_size=5,stride=1,padding=0),
            nn.AvgPool2d(kernel_size=2, stride=2, padding=0)
        )
        self.fc_unit=nn.Sequential(
            nn.Linear(16*53*53,120),
            nn.ReLU(),
            nn.Linear(120,84),
            nn.ReLU(),
            nn.Linear(84,self.lens)
        )

    def forward(self,x):
        # x = x.to(device)
        x=self.conv_unit(x)

        x=x.view(-1,16*53*53)
        logits = self.fc_unit(x)
        return logits


def main():
    net =Lenet5()
    tmp = torch.randn(32,3,224,224)
    out = net(tmp)
    print(out.shape)


if __name__ == '__main__':
    main()