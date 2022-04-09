from django.contrib import admin
from django.urls import path
from . import views
from . import test
from . import train
urlpatterns = [
    path('<int:sid>/<str:name>', views.index , name ="index"),
    path('getDataset', views.getDataset),
    path('getarray', train.sendarray),
    path("test/<str:pic>/<str:dataset>/<str:model_name>",test.main),
    path("train/<int:isTrain>/<str:dataset_names>/<str:model_names>/<int:isFirst>/<int:epoch>", train.main)
]