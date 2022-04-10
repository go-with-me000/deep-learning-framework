# 毕设

## python

### test

- ##### 第一个参数：传入图片的路径名

- ##### 第二个参数：传入的数据集文件名

- ##### 第三个参数：加载的模型名

###### python test.py pika.jpg pokeman best2.mdl

http://127.0.0.1:8085/myapp/test/pika.jpg/pokeman/best2.mdl

### train_transfer

- ##### 传入是否训练  0——不训练， 1——训练  

- ##### 传入的数据集文件名

- ##### 加载的模型名

- ##### 是否是第一次   1——第一次   0 不是第一次

- epoch，轮数

###### python train.py 0 pokeman pokeman.mdl 0  2

- 1-1代表着第一次训练
- 0-1不训练却是第一次，不存在
- 1-0不是第一次，但是要训练，这次情况
- 0-0不是第一次也不训练，测试模型

http://127.0.0.1:8085/myapp/train/1/pokeman/pokeman.mdl/1/1

http://127.0.0.1:8085/myapp/train/1/pokeman/pokeman.mdl/0/2

http://127.0.0.1:8085/myapp/train/0/pokeman/pokeman.mdl/0

http://127.0.0.1:8085/myapp/train/1/flowers/flower.mdl/1

http://127.0.0.1:8080/train?isTrain=0&isFirst=0&dataset_name=pokeman&model_name=pokeman.mdl

## Django

- 创建项目
  - django-admin startproject [project_name]
- 启动项目
  - python  manage.py  runserver 8085
  - python  manage.py  runserver 0.0.0.0:8085(支持远程访问)
- 创建应用
  - python manage.py startapp [a]
  - --trusted-host mirrors.aliyun.com



## 数据库

- #### 用户(user)

  - user_id，account，password

- #### 模型(model)

  - model_id, user_id, model_name, dataset_path, accuracy, network