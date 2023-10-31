# NOTE
- [NOTE](#note)
- [stage0: pose-guided human embedding](#stage0-pose-guided-human-embedding)
  - [difference](#difference)
- [stage1:](#stage1)
  - [motivation](#motivation)
- [stage2: 解决flicker的问题](#stage2-解决flicker的问题)
- [stage3: 一个统一的图像编辑的范式, crossnet](#stage3-一个统一的图像编辑的范式-crossnet)
  - [insight](#insight)
  - [design](#design)
  - [pipeline](#pipeline)
  - [设计](#设计)
  - [问题](#问题)
- [Reference](#reference)
  - [1.  ReVersion: Diffusion-Based Relation Inversion from Images](#1--reversion-diffusion-based-relation-inversion-from-images)
    - [pipeline](#pipeline-1)
  - [2. Inversion-Based Style Transfer with Diffusion Models](#2-inversion-based-style-transfer-with-diffusion-models)
    - [abstraction](#abstraction)
    - [pipeline](#pipeline-2)
  - [3.  UPGPT: Universal Diffusion Model for Person Image Generation, Editing and Pose Transfer](#3--upgpt-universal-diffusion-model-for-person-image-generation-editing-and-pose-transfer)
    - [Abstract](#abstract)
    - [Pipeline](#pipeline-3)
    - [difference](#difference-1)
  - [4.  Tune-A-Video: One-Shot Tuning of Image Diffusion Models for Text-to-Video Generation](#4--tune-a-video-one-shot-tuning-of-image-diffusion-models-for-text-to-video-generation)
    - [abstract](#abstract-1)
    - [method](#method)
    - [difference](#difference-2)
  - [5.  IVLR：Conditioning Method for Denoising Diffusion Probabilistic Models](#5--ivlrconditioning-method-for-denoising-diffusion-probabilistic-models)
    - [Abstract](#abstract-2)
    - [PIPELINE](#pipeline-4)
    - [questions](#questions)
  - [6.  SDEdit: Guided Image Synthesis and Editing with Stochastic Differential Equations](#6--sdedit-guided-image-synthesis-and-editing-with-stochastic-differential-equations)
  - [7.  RePaint: Inpainting using Denoising Diffusion Probabilistic Models](#7--repaint-inpainting-using-denoising-diffusion-probabilistic-models)
  - [8. HumanSD: A Native Skeleton-Guided Diffusion Model for Human Image Generation](#8-humansd-a-native-skeleton-guided-diffusion-model-for-human-image-generation)
    - [Abstract](#abstract-3)
    - [Pipeline](#pipeline-5)
      - [training](#training)
    - [insight](#insight-1)
    - [rethinking](#rethinking)
  - [9. Crossway Diffusion: Improving Diffusion-based Visuomotor Policy via Self-supervised Learning](#9-crossway-diffusion-improving-diffusion-based-visuomotor-policy-via-self-supervised-learning)
    - [Abstract](#abstract-4)
    - [rethinking](#rethinking-1)

# stage0: pose-guided human embedding

把人替换到给定场景里面去

<img src="./images/pose-based.jpg" />

- 第一个问题: 怎么保证bedrock image环境不变而人变化, 并且appearance变化的方向是向着target的方向变化
- 第二个问题: 怎么样引入number这个信息(cross + gram矩阵, 设计一个新的loss)
- 第三个问题: 建图的风格问题

## difference
1. 与pose-based的区别:
   pose-based的方法是动作迁移的常见做法; 但如果想要受控, pose-guide需要 
   
   (1)  以everybody dance now为例，想要一个特定的人物出现在我们设置好的bedrock中, 得先训练一个可以画出对应人物的GAN，这需要很多迭代步, 我希望能够

   (2) pose-based只能在具有相似骨架结构的物体间迁移，主要是人体

   (3) pose-based的风格问题, 压缩成pose figure没办法保留场景的信息

   (4) pose-based没办法做强遮挡和强关系的, 比如趴着这个视角, 就很难做到

2. 与 Tune-A-Video 的对应任务的区别:
   我希望把确定的物体放进图像里面, 而不是由文字描述这个多射, 因为这样生成的人仍然是不可控的

3. 和reversion的区别




# stage1:

## motivation

去掉pose-guide, 用 text-inversion代替

1. 细粒度的动作描述应该不会与动词描述离得太远

pose-guide太麻烦了, 图像迁移需要人工提供figure. 视频迁移需要不断的生成figure, 并且每迁移一个新人就需要反向过程迭代[[4]](#4-tune-a-video-one-shot-tuning-of-image-diffusion-models-for-text-to-video-generation), 越复杂的动作pose设计越麻烦
  纯pose-guide会丢失信息, 比如图像的风格, 图像的环境信息, 小物体等
  并且, 更换动作对象的时候可以不动参数, 把inversion的能力固定在一个branch里, 直接输入一个bedrock图, 一个待迁移对象图, 把物体通过一次前向过程嵌入到bedrock里, 输出迁移好的图片
  - 第一个问题: 怎么样引入number这个信息(cross + gram矩阵, 设计一个新的loss)
  - 第二个问题: 怎么保证人的looking不变, text reversion, 用一种特定的prompt
  (xxx is doing sth like < p >)
  

# stage2: 解决flicker的问题

  - 第一, 逐帧视频生成的闪动问题, 我们把之前生成的k帧图像加上time embedding放一个cross进去重建, 利用重建损失梯度和生成去噪损失梯度之间的博弈, 减少闪动的问题 (two scene reconstruct)

# stage3: 一个统一的图像编辑的范式, crossnet

找到一种端到端的方式, 将一个特定的人物或者事物 "嵌入" 到一张给定的图像中, 或者一段给定的视频中, 代替图像中已有的物体, 嵌入时需要保证这个嵌入的对象的状态和原本在这个位置的物体一致, 这两个人物有一个共同的语义特征(位置和动作), 并且保留嵌入物体的特征和保持非嵌入部分的语义信息不变

这样目的的任务有很多
1. 动作迁移
   单物体的动作迁移，让给定的一个人做出另一张图给定的动作； 
   多物体动作迁移，更改其中一个或多个目标为我们给定的目标

2. relation transfer
   几个物体之间的关系换成另外几个物体的关系
3. 视频生成
   生成不同的对象做出同样动作的视频

这些统一的点就是我们需要把一些语义信息加到（或者替代）一个原始的bedrock上，这个原始的bedrock可以被任意的划分，比如动作迁移中我们希望更改的是人的部分的semantic information，图像编辑则是希望改变某个位置的，relation transfer
bedrock中的部分信息需要被保留（可以用incontext的方法），部分信息需要被替代，在替代和修改信息的时候，要保持其他的语义信息不变

并列的inversion cross, 每个cross确定不同的人物由text作为总触发, 然后用户可以提供image examples, 有新的loss就可以加进去
  - 第一个问题: trigger text要怎么分析
  - 第二个问题: 不同的loss分别要排列, 优化哪一部分参数

## insight

如果一个扩散模型在进行不同t的扩散步骤中失去特征的顺序是可以被预测到的，那么这个语义加法就是可以实现的，输入cross attention的内容不一样可以吧(crossnet)



## design

1. clip image encoder提取图像特征:
  
+ 我们看一看同一动作但是不同人物的两个vector的位置 **(reversion experiment 1 做法 [[1]](#reversion-diffusion-based-relation-inversion-from-images))**, 是不是同一个动作的图本身就具有很好的特征一致性

+ *expectation: 大概不会, 因为受到appearance的影响太大*

+ 如果是, 那么动作迁移可以直接和几个相同的动作的聚类中心一起做(维护prototype)
  如果不是, 那么就需要引入过滤器(appearance filter), 我们提出的结构是希望把source image中的不需要的东西滤掉(如果是这样的话, 是不是用一下DINO先做裁剪或者分割), 只保留人的appearance, 而且通过引入text inversion把它调到可以根据这个生成的text prompt可以还原出人的appearance, 并通过motion branch保留动作和分布, 也就不需要了pose和任何的旁路知识, 而且通过直接修采样器(在随机噪声上下功夫)能解决这个consistency的问题, 如果是视频理解人物, 我们希望保留帧与帧之间的一些细微的一致性, 这在inversion-based style transfer这篇文章里讲的是细节的信息应该是来自于随机噪声的.
  对于一个人的appearance的描述也应该是比较复杂的, 如果单个 inversion token是不能很好逼近效果的话, 是不是需要多个inversion token? 

1. 把diffusion noise的 $x_t$ 扔进动作分类器(或者聚类), 能不能把不同的动作分开来? 
- 可以分开的话, 随机噪声中就包含着这个动作的有关信息, 采样器应该可以把这个信息采出来?
  - motion branch我希望执行的是将motion source扩散之后的噪声作为初始噪声 $x_t$ , 这当然保留了动作, 但是同时也保留了source image的appearance, 应该对这个 $ x_t $ 做什么处理把它变成 $ x^,_t $ , 把appearance的信息去掉, 这个需要一个监督方法, 用单纯的恢复图像不行
  > 对初始噪声 $x_t$ 用ReVersion的办法, 引入text, 即使text discription没有对appearance的详细描述, 但描述appearance的向量应该也和noun space和adjective space离得很近
  > 所以就是用词性监督来做, contrastive loss把inversion token (或者再经过text encoder翻译一遍的representation) 和名词域和形容词域拉远 (sub-word能不能有这种带形容词的名词词组?) , 和动词域的【均值】拉近
  > 这还不够吧, text discription中的粒度并不够细, motion可以同时做steer supervise

  > 第二个办法是直接给采样器引入参数, 让他能更好的拿我们需要的信息
  - 所以

- 不可以分开的话, 那么动作这个信息来源于什么地方呢?  

3. 类似 IVLR[[5]](#ivlrconditioning-method-for-denoising-diffusion-probabilistic-models) 和 SDEdit[[6]](#sdedit-guided-image-synthesis-and-editing-with-stochastic-differential-equations) 
   动作和appearance是不是在noise stages中处于不同的位置, 基本上是的, [5,6]中的全局修改几乎不改变空间位置关系

## pipeline

两个branch


## 设计

(分辨率要高一点, 不能是大场景小人物, 而应该是人物占主体, 并且做了一个动作 ____ 可以从电影里找找?)

1. 所以实验可以设计一个, 通过调整不同的随机噪声【这就是采样器的问题】, 生成同一动作(pose bone一样)的不同的人的图片
如果这个可以做到, 那么只需要通过引入另一个人的appearance就能做到迁移了————但是学appearance是要做什么呢？

2. 第二种思路是先保证appearance, 然后引入的是动作(这其实就是pose-based)

第一种思路, 我们希望两个分支, source分支提取物体的appearance, motion target分支保留动作
motion target应该能够

## 问题

- tuning a video是不是已经做过了这件事，我和他的区别仅仅是我能使得特定的人做video(或者图片)中做的事情?
- 最差的情况, 即使我用pose-based的东西, 能跟everybody dance的区别仅在于我可以通过只给一张动作示意图和一张人物图就可以生成, novelty够不够?

- ddpm的模型运行过程

如果这个东西可以做, 就有几个思路
做不了, 得去找新的任务

----
# Reference

## 1.  ReVersion: Diffusion-Based Relation Inversion from Images

### pipeline

<img src="./images/ReVersion-Diffusion-Based-Relation-Inversion from Images.png" />


也是个恢复任务, 

- 将视觉guidance引入diffusion过程
- 提出了关系建模的方法, reversion认为关系可以由一个或一个序列的介词构成, 而从clip对token的分解实验中, 发现词向量是按照语素分开的, 而每一个语素域中的向量之间的距离是很大的(第一个实验, 度量同样的介词token之间的dist), 所以由这种稀疏性, 他们假设介词域中肯定存在可以精确描述关系的 "介词"

----

## 2. Inversion-Based Style Transfer with Diffusion Models

### abstraction

> We believe that the uniqueness of an artwork lies precisely in the fact that it cannot be adequately explained with normal language.

这篇文章使用inversion的办法获取style, 并且在保证图片内容和对象不变的情况下, 把style换了



### pipeline

<img src="./images/Inversion-Based Style Transfer with Diffusion Models.png"></img>



训练的时候, x和y是一致的, 所以训练的时候是一个恢复任务

tokenization说的是把word或者sub-word变成预先定义好字典的序号(ids)
每一个就有经clip生成的representation


这一篇没有appearance的变化，也就不需要从image到latent space的语义压缩, 他直接通过在图上进行噪声的扩散来保持原图的特征, 这是我看这篇文章的初衷, 我希望找到一个在latent-space中也能够保留appearance的办法

调整采样器是合理的, 我们希望画一个人, 让模型学会像人一样画画, 注意动作的sketch, 把人的appearance像上色一样上上去.

>  **textual inversion**
>
> CLIP文本编码器包含两个过程: tokenization 和 parameterization
> 
> 比较经典的办法在《an image is worth a word》中有提过， 直接优化inversion token $\hat{v}$
> $$ \hat{v} = \mathop{\arg\min}_{v} \mathbb{E}_{z,x,y,t} [ \| \epsilon - \epsilon_{\theta}(z_t,t,v_{\theta}(y)) \|^2_2 ] $$ 
> 直接用LDM loss来监督并不能优化出一个很好的inversion token
>  
> 用多层的attention是要有训练过程的呀, 

随机反转过程到底做了什么

----

## 3.  UPGPT: Universal Diffusion Model for Person Image Generation, Editing and Pose Transfer

### Abstract


### Pipeline

### difference

- 首先动机是一样的, 动作迁移这件事情, 很难用语言来描述, 因为语言和图像是一个一对多映射
- 引入语言到底能对editing起到多大的作用



----

## 4.  Tune-A-Video: One-Shot Tuning of Image Diffusion Models for Text-to-Video Generation

### abstract

基于两个基本的观察: 
1. T2I model可以生成静止但带有动作语义的图像
2. 扩展的T2I model可以生产连续性和一致性很好的图片



<img src="./images/Tuning-a-video.jpg" />

### method
$V = \{ v_i | i \in [1,m] \} $ 代表一个由m帧组成的视频
 $P$ 代表描述 $V$ 的prompt
目的是编辑 $P^*$ 来生成不同的 $V^*$ 


### difference

这是用文字执行修改, 还不错, 但是文字是一对多映射

## 5.  IVLR：Conditioning Method for Denoising Diffusion Probabilistic Models

### Abstract

针对DDPM随机性太高无法确定性生成的痛点. 本文认为前向原信息的丢失过程是先丢失高频信息再丢失低频信息, 后向信息逐渐从纯噪声中补全, 这种补全是先补全低频信息, 再补全高频信息.

如果能够记录前向过程每一步的噪声图像, 将其与后向过程噪声图像混合, 就可以影响后向生成的结果

### PIPELINE
<img src="./images/IVLR.png" height=40%/>

### questions

motion和appearance是属于高维信息还是低维信息?

## 6.  SDEdit: Guided Image Synthesis and Editing with Stochastic Differential Equations

## 7.  RePaint: Inpainting using Denoising Diffusion Probabilistic Models

## 8. HumanSD: A Native Skeleton-Guided Diffusion Model for Human Image Generation

### Abstract
control-net和T2I-adapter的问题在于加入的调整分支和原始frozen的信息冲突(branch conflict), 
> 我们讲的过滤器的形式, 可以解决这个问题吗?

- 没有在training和inference的时候在SD引入pose, 而是直接在SD上finetune,不可能啊

- 设计了一种新的heatmap-guided 去噪损失

### Pipeline

<img src="./images/HumanSD.png" />

#### training
training task是重建任务, 引入figure和noise做concatenation

直接把noise和figure latent embedding拼在一起, 这样也可以

### insight
通过微调SD, 确实是可以得到一个

- 不引入任何的多余信息, 就可以画好复杂的动作? 这个loss这么强?
- 
### rethinking
动作迁移仍然可以做, 这就是关于task的问题, 没有不能做的task

## 9. Crossway Diffusion: Improving Diffusion-based Visuomotor Policy via Self-supervised Learning

### Abstract

### rethinking

crossway应该只有训练的时候用, inference时是冻住参数