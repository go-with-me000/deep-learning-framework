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



def index(request,sid,name):
    data = {
        'patient_name': '张三',
        'age': '25',
        'patient_id': '19000347',
        '诊断': '上呼吸道感染',
    }
    return JsonResponse(data)

def train(request,sid,name):
    data = {
        'patient_name': '张三',
        'age': '25',
        'patient_id': '19000347',
        '诊断': '上呼吸道感染',
    }
    return JsonResponse(data)
def getDataset(request):
    name2label = {}
    path="D:\programming\python\deep-learning-exam\graduation\django\deep\myapp\dataset"
    for name in sorted(os.listdir(path)):

        name2label[len(name2label.keys())] = name

    print(name2label)
    print(os.getcwd())
    return JsonResponse(name2label)