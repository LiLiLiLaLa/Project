

## 一、用户相关接口

> **全是POST请求， code为0才是正常返回情况**

### 1、用户注册

不允许注册管理员，即userIsman字段必须是1	(1表示普通客户，0表示管理员)

```
/user/register/
```
参数

```json
userId 用户Id：String
userPassword 用户密码：String
userName 用户姓名：String
userAddress 用户地址：String
userIsman 是否是管理员(1表示普通客户，0表示管理员)：Integer
userPhone 用户电话：String
userIcon 用户头像：String
```

返回值：

```json
成功
{
	"code":0,
	"data":{
		"userAddress":"安徽某个地方",
		"userIcon":"https://s2.ax1x.com/2019/05/31/VQxJZn.png",
		"userId":"00006",
		"userName":"VAE",
		"userPassword":"123456",
		"userPhone":"13772812803"
	},
	"msg":"注册成功"
}

失败
{
	"code":1,
	"msg":"此账号已存在"
}
```



### 2、用户登录

```
/user/login/
```
参数

```
userId	用户Id
userPassword	用户密码
```

返回值

```json
成功
{
	"code":0,
	"data":{
		"userAddress":"管理员地址",
		"userIcon":"https://s2.ax1x.com/2019/05/31/VQxJZn.png",
		"userId":"00000",
		"userIsman":0,
		"userName":"管理员",
		"userPassword":"123456",
		"userPhone":"15291418231"
	},
	"msg":"登陆成功"
}

失败
{
	"code":1,
	"msg":"用户名或密码错误"
}
```




### 3、用户注销
```
/user/logout/
```
参数

```
无
```

返回值

```json
成功
{
	"code":0,
	"msg":"移除session成功"
}
失败
{
	"code":1,
	"msg":"没有需要移除的session"
}
```



### 4、添加商品到购物车

添加购物车失败的原因可能是：

* 用户未登录

* 商品Id不存在
* 商品库存不足

```
/shopping/add
```

参数

```
productId 商品的Id：String类型
count 商品数量：Integer类型
```

返回值

```json
成功
{
	"code":0,
	"msg":"添加购物车成功"
}

失败
{
	"code":1,
	"msg":"添加购物车失败"
}
```

### 5、从购物车删除商品

一次请求只能删除一个productId对应的商品，相当于是productId对应的商品数量减一，如果删除失败，则可能的原因可能是：

* 用户未登录
* 商品Id对应的商品不存在
* 商品已经被全部移除

```
/shopping/delete
```

参数

```
productId 商品的Id：String类型
```

返回值

```json
成功
{
	"code":0,
	"msg":"从购物车删除成功"
}
失败
{
	"code":1,
	"msg":"从购物车删除失败"
}
```



### 6、购物车商品展示

失败的原因可能是：

* 用户未登录

```
/shopping/list
```

参数

```
无
```

返回值

```json
成功
{
	"code":0,
	"data":[
		{
			"proList":{
				"categoryType":2,
				"createTime":1558622138000,
				"productDescription":"优良的观花藤木植物，缠绕茎，羽状复叶，小叶长椭圆形。",
				"productIcon":"https://s2.ax1x.com/2019/05/23/VCMZzd.png",
				"productId":"1558575338397",
				"productName":"紫藤萝",
				"productPrice":35.00,
				"productStock":1024,
				"updateTime":1558622138000
			},
			"proNum":20 商品数量
		}
	],
	"msg":"展示成功"
}

失败
{
	"code":1,
	"msg":"无数据"
}
```

### 7、购物车结算价格

失败的原因可能是：

* 用户未登录

* 购物车无任何商品

```
/shopping/amount
```

参数

```
无
```

返回值

```json
成功
{
	"code":0,
	"data":35.0,
	"msg":"成功"
}

失败
{
	"code":1,
	"msg":"失败"
}
```



### 8、创建订单

创建订单是直接从购物车创建，所以购物车内的商品都算在订单内，如果失败可能的原因是：

* 用户未登录
* 购物车未加入任何商品
* 购物车商品数目已经超出库存

```
/order/create
```

参数

```
无(前提是购物车有商品)
```

返回值

```json
成功
{
	"code":0,
	"data":{
    "masterOrderId":"1559263007925593743", 主订单的Id
		"OrderAmount":440.00, 订单总金额
		"OrderInfo":[ 订单的详细信息
			{
				"proList":{
					"categoryType":3,	商品类型
					"createTime":1558622138000,	商品的创建时间
					"productDescription":"放在油里面煎一下，然后加我在里面做出来的味道才会特别的好。", 商品描述
					"productIcon":"https://s2.ax1x.com/2019/05/23/VC1AiT.png", 商品图片
					"productId":"1558575338537", 商品Id
					"productName":"农家乌鸡", 商品名称
					"productPrice":50.00, 商品价格
					"productStock":100, 商品库存
					"updateTime":1558967702000 商品信息最后更新时间
				},
				"proNum":8 购买商品的数量
			},
			{
				"proList":{
					"categoryType":2,
					"createTime":1558622138000,
					"productDescription":"叶片线状披针形，顶端长渐尖，基部稍成短鞘，中脉明显。",
					"productIcon":"https://s2.ax1x.com/2019/05/23/VC1PZq.png",
					"productId":"1558575338519",
					"productName":"康乃馨",
					"productPrice":20.00,
					"productStock":500,
					"updateTime":1558967708000
				},
				"proNum":2
			}
		]
	},
	"msg":"创建新订单成功"
}

失败
{
	"code":1,
	"msg":"创建新订单失败"
}
```



### 9、支付订单

此接口用于支付订单，创建订单之后会进行支付订单，默认跳转支付包扫码支付页面！

```
/pay/affirmPay
```

参数

```
masterOrderId 订单Id：String
```

返回值

```
支付宝页面跳转
```

### 10、支付宝回调接口(不用管)

```
/pay/payFinish
```

### 11、查询订单支付状态

失败的原因可能是：

* 用户未登录
* 订单Id对应的订单不存在

```
/pay/payStatus
```

参数

```
masterOrderId 订单Id：String
```

返回值

```json
成功
{
	"code":0,
	"data":0,  0表示等待支付，1表示已经支付
	"msg":"查询成功"
}

失败
{
	"code":1,
	"msg":"无此订单"
}
```



### 12、取消订单

这个是用户自己取消订单的接口，如果取消失败，失败的原因可能是：

* 用户未登录
* 订单号orderMasterId不存在

```
/order/cancel
```

参数

```
orderMasterId 订单Id：String
```

返回值

```json
成功
{
	"code":0,
	"data":{
		"buyerId":"00002",
		"createTime":1559600745000,
		"orderAmount":1.50,
		"orderId":"1559553945623292382",
		"orderStatus":2,
		"payStatus":1,
		"updateTime":1559679859000
	},
	"msg":"成功"
}

失败
{
	"code":1,
	"msg":"取消订单失败"
}
```



### 13、预约实践基地

```
/practice/addPractice
```

参数

```
practiceBaseId 实践基地Id：String
```

返回值：

```json
成功
{
	"code":0,
	"msg":"成功"
}

失败
{
	"code":1,
	"msg":"未登录或信息不完善"
}
{
	"code":2,
	"msg":"预约人数已满"
}
```

### 14、查看已预约实践基地

```
/practice/showAllPractice
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"baseAddress":"陕西省西安市",
			"baseDescription":"创新实践基地是由西安\b某大学学生工作处实施和运作的以学生为中心",
			"baseEnd":1559784323000,
			"baseIcon":"https://s2.ax1x.com/2019/06/06/VdXRk8.png",
			"baseId":"1559837078331742182",
			"baseJoin":4,
			"baseMaxpeople":200,
			"baseName":"西安创新实践基地",
			"baseStart":1559762723000
		},
		{
			"baseAddress":"陕西省西安市",
			"baseDescription":"创新实践基地是由西安\b某大学学生工作处实施和运作的以学生为中心",
			"baseEnd":1559784323000,
			"baseIcon":"https://s2.ax1x.com/2019/06/06/VdXRk8.png",
			"baseId":"1559837217006752140",
			"baseJoin":4,
			"baseMaxpeople":300,
			"baseName":"西安创新实践基地",
			"baseStart":1559762723000
		}
	],
	"msg":"成功"
}

失败
{
	"code":1,
	"msg":"未登录或信息不完善"
}
```

### 15、领养植物

```
/botany/adopt
```

参数

```
botanyId 植物Id：String
```

返回值

```json
{
	"code":0,
	"data":{
		"botanyDescription":"亚热带和热带常用的观花植物。喜温暖和充足的阳光，不耐寒。对土壤要求不严，在疏松肥沃、排水良好的沙土壤中生长最佳，也适应于肥沃粘质土壤生长。",
		"botanyIcon":"https://s2.ax1x.com/2019/06/07/V0iYlV.png",
		"botanyId":"1559887731124394872",
		"botanyName":"美人蕉",
		"botanyNum":98
	},
	"msg":"成功"
}

{
	"code":1,
	"msg":"用户未登录"
}

{
	"code":2,
	"msg":"已领养过该植物"
}
```



### 16、查看已领养植物

```
/botany/showMyAll
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"botanyDescription":"亚热带和热带常用的观花植物。喜温暖和充足的阳光，不耐寒。",
			"botanyIcon":"https://s2.ax1x.com/2019/06/07/V0iYlV.png",
			"botanyId":"1559887731124394872",
			"botanyName":"美人蕉",
			"botanyNum":98
		},
		{
			"botanyDescription":"佛甲草去年一推出就广受好评，因为它真的很好养。",
			"botanyIcon":"https://s2.ax1x.com/2019/06/07/V0i4kd.png",
			"botanyId":"1559895414899956393",
			"botanyName":"佛甲草",
			"botanyNum":49
		}
	],
	"msg":"成功"
}
```

### 17、用户采摘预约

```
/pick/addMyPick
```

参数

```
pickProduct 采摘作物Id：String
pickTime 采摘时间：Unix时间戳 Long
```

返回值

```json
{
	"code":0,
	"msg":"成功"
}

{
	"code":1,
	"msg":"用户未登录"
}

{
	"code":2,
	"msg":"重复预约"
}
```

### 18、查看已预约采摘信息

```
/pick/findMyPick
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"pickCheck":0,	//3表示已经取消
			"pickId":"1559955656608783994",
			"pickTime":1559956000,
			"pickUser":"00001",
			"productDescription":"芭蕉科芭蕉属植物，又指其果实，热带地区广泛种植。",
			"productIcon":"https://s2.ax1x.com/2019/05/31/Vlwj9H.png",
			"productId":"1559286382724",
			"productName":"香蕉",
			"productPrice":3.00,
			"productStock":500
		}
	],
	"msg":"成功"
}


{
	"code":1,
	"msg":"用户未登录"
}
```

###19、取消预约采摘

```
/pick/cancelMyPick
```

参数

```
pickId 预约的Id：String
```

返回值

```json
{
	"code":0,
	"msg":"成功"
}

{
	"code":1,
	"msg":"用户未登录"
}

{
	"code":2,
	"msg":"参数不正确"
}

{
	"code":3,
	"msg":"取消预约失败"
}
```



## 二、管理员接口

### 1、删除商品

失败的原因可能是：

- 管理员未登录
- 商品Id对应的商品不存在

```
/product/delete
```

参数

```
productId 商品Id：String
```

返回值

```json
成功
{
	"code":0,
	"msg":"删除成功"
}

失败
{
	"code":1,
	"msg":"对应商品不存在"
}
```



### 2、添加商品

失败的原因可能是：

- 管理员未登录
- 新商品参数不完备

```
/product/add
```

参数

```
productName 商品名称：String
productPrice 商品价格：Double
productStock 商品库存：Integer
productDescription 商品描述：String
productIcon 商品图片：String
categoryType 商品类型：Integer
productGrade 商品评分：String
```

返回值

```json
{
	"code":0,
	"data":{
		"categoryType":1, 商品类型
		"productDescription":"芭蕉科芭蕉属植物，又指其果实，热带地区广泛种植。", 商品描述
		"productGrade":"4.6", 商品评分
		"productIcon":"https://s2.ax1x.com/2019/05/31/VlEuvt.png", 商品图片
		"productId":"1559289524545", 商品Id
		"productName":"香蕉2", 商品名称
		"productPrice":3, 商品价格
		"productStock":500 商品库存
	},
	"msg":"添加成功"
}
```



### 3、修改商品

失败的原因可能是：

* 管理员未登录

* 修改后的信息不完善

```
/product/update
```

参数

```
productId(此Id由商品列表中的Id得到)
productName
productPrice
productStock 商品库存：Integer
productDescription 商品描述：String
productIcon
categoryType
productGrade 商品评分：String
```

返回值

```json
成功
{
	"code":0,
	"data":{
		"categoryType":1, 商品类型
		"productDescription":"芭蕉科芭蕉属植物，又指其果实，热带地区广泛种植。", 商品描述
		"productGrade":"4.6", 商品评分
		"productIcon":"https://s2.ax1x.com/2019/05/31/VlEuvt.png", 商品图片
		"productId":"1559289524545", 商品Id
		"productName":"香蕉2", 商品名称
		"productPrice":3, 商品价格
		"productStock":500 商品库存
	},
	"msg":"添加成功"
}

失败
{
	"code":1,
	"msg":"无对应商品"
}
```

### 4、查询所有订单

失败的原因可能是：

* 管理员账户未登录

```
/order/allList
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"buyerId":"00001",
			"createTime":1559162252000,
			"orderAmount":300.00,
			"orderId":"1559115452028993038",
			"orderStatus":0,
			"payStatus":0,
			"updateTime":1559162252000
		},
		{
			"buyerId":"00001",
			"createTime":1559163904000,
			"orderAmount":200.00,
			"orderId":"1559117104671919428",
			"orderStatus":0,
			"payStatus":0,
			"updateTime":1559163904000
		}
	],
	"msg":"查询所有订单成功"
}
```

### 5、查询订单详情

失败的原因可能是：

* 管理员未登录
* 订单Id无对应的订单

```
/order/orderDetail
```

参数

```
orderMasterId 订单Id:String
```

返回值

```json
成功
{
	"code":0,
	"data":[
		{
			"createTime":1559163904000, 订单创建时间
			"detailId":"1559117104684315207", 订单详情条目Id
			"orderId":"1559117104671919428", 主订单Id
			"productId":"1558575338537", 商品Id
			"productNum":3, 商品数量
			"updateTime":1559163904000 最后更新时间
		},
		{
			"createTime":1559163904000,
			"detailId":"1559117104701244937",
			"orderId":"1559117104671919428",
			"productId":"1558575338525",
			"productNum":2,
			"updateTime":1559163904000
		}
	],
	"msg":"查询订单详情成功"
}

{
	"code":1,
	"msg":"查询订单失败"
}
```

### 6、查询订单

根据条件查询出的订单都是在List集合里面，失败的原因可能是：

* 管理员账户未登录

#### ① 根据订单状态查询

```
/order/allListByOrderStatus
```

参数

```
orderStatus 订单状态：Integer (0、新订单 1、已完结 2、已取消)
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"buyerId":"00001", 买家Id
			"createTime":1559162252000, 创建时间
			"orderAmount":300.00,  订单金额
			"orderId":"1559115452028993038", 订单Id
			"orderStatus":0, 订单状态
			"payStatus":0, 支付状态
			"updateTime":1559162252000 最后更新时间
		},
		{
			"buyerId":"00001",
			"createTime":1559163904000,
			"orderAmount":200.00,
			"orderId":"1559117104671919428",
			"orderStatus":0,
			"payStatus":0,
			"updateTime":1559163904000
		}
	],
	"msg":"查询对应状态订单成功"
}
```



#### ② 根据用户查询

```
/order/allListByBuyerId
```

参数

```
buyerId 用户(买家)Id：String
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"buyerId":"00001",
			"createTime":1559162252000,
			"orderAmount":300.00,
			"orderId":"1559115452028993038",
			"orderStatus":0,
			"payStatus":0,
			"updateTime":1559162252000
		},
		{
			"buyerId":"00001",
			"createTime":1559163904000,
			"orderAmount":200.00,
			"orderId":"1559117104671919428",
			"orderStatus":0,
			"payStatus":0,
			"updateTime":1559163904000
		}
	],
	"msg":"查询对应用户订单成功"
}
```



### 7、完结订单

失败的原因可能是：

* 管理员账户未登录
* 订单Id无对应的订单

```
/order/finish
```

参数

```
orderMasterId 订单Id：String
```

返回值

```json
成功
{
	"code":0,
	"data":true,
	"msg":"完结订单成功"
}

失败
{
	"code":1,
	"data":false,
	"msg":"完结订单失败"
}
```



### 8、取消订单

失败的原因可能是：

- 管理员账户未登录
- 订单Id无对应的订单

这个是管理员取消订单的接口

```
/order/cancelOrder
```

参数

```
orderMasterId 订单Id：String
```

返回值

```json
成功
{
	"code":0,
	"data":{
		"buyerId":"00002",
		"createTime":1559600745000,
		"orderAmount":1.50,
		"orderId":"1559553945623292382",
		"orderStatus":2,
		"payStatus":1,
		"updateTime":1559679859000
	},
	"msg":"成功"
}

失败
{
	"code":1,
	"msg":"取消订单失败"
}
```

### 9、增加/修改实践基地

```
/practice/add
```

参数

```
baseId 基地编号：String(可不传，修改时必须传)
baseName 基地名称：String
baseAddress 基地地址：String
baseDescription 基地描述：String
baseJoin 已参加人数：Integer
baseMaxpeople 最多参加人数：Integer
baseStart 开始时间：Long类型时间戳
baseEnd 结束时间：Long类型时间戳
baseIcon 实践基地图：String
```

返回值

```json
成功
{
	"code":0,
	"msg":"成功"
}
```



### 10、删除实践基地

```
/practice/delete
```

参数

```
practiceBaseId 实践基地Id：String
```

返回值

```json
{
	"code":0,
	"msg":"成功"
}
```

### 11、增加领养植物

```
/botany/add
```

参数

```
botanyId 植物Id(随便给)
botanyName 名称：String
botanyIcon 图片：String
botanyNum 植物库存:Integer
botanyDescription 植物描述:String
```

返回值

```json
{
	"code":0,
	"msg":"成功"
}
```

### 12、删除领养植物

```
/botany/delete
```

参数

```json
botanyId 植物Id：String
```

返回值

```json
{
	"code":0,
	"msg":"成功"
}

{
	"code":1,
	"msg":"参数错误"
}
```

### 13、修改领养植物

```
/botany/update
```

参数

```
botanyId 植物Id
botanyName 名称：String
botanyIcon 图片：Integer
botanyNum 植物库存:Integer
botanyDescription 植物描述:String
```

返回值

```json
{
	"code":0,
	"msg":"成功"
}

{
	"code":3,
	"msg":"无此植物信息"
}
```



### 14、查看所有采摘预约信息

也就是查看所有的用户的预约信息

```
/pick/checkShow
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"pickCheck":0,
			"pickId":"1559955656608783994",
			"pickTime":1559956000,
			"pickUser":"00001",
			"productDescription":"芭蕉科芭蕉属植物，又指其果实，热带地区广泛种植。",
			"productIcon":"https://s2.ax1x.com/2019/05/31/Vlwj9H.png",
			"productId":"1559286382724",
			"productName":"香蕉",
			"productPrice":3.00,
			"productStock":500
		},
		{
			"pickCheck":0,
			"pickId":"1559955844861188079",
			"pickTime":1559956000,
			"pickUser":"00001",
			"productDescription":"叶片线状披针形，顶端长渐尖，基部稍成短鞘，中脉明显。",
			"productIcon":"https://s2.ax1x.com/2019/05/31/VlwDcn.png",
			"productId":"1558575338519",
			"productName":"康乃馨",
			"productPrice":3.50,
			"productStock":500
		},
		{
			"pickCheck":3,
			"pickId":"1559955969236728988",
			"pickTime":1559956000,
			"pickUser":"00001",
			"productDescription":"原产地中海沿岸及小亚细亚一带",
			"productIcon":"https://s2.ax1x.com/2019/05/31/Vlw439.png",
			"productId":"1558575338525",
			"productName":"风信子",
			"productPrice":3.00,
			"productStock":2000
		}
	],
	"msg":"成功"
}
```

### 15、修改预约信息状态

预约信息状态 0 新预约、1 审核通过、2 未通过、3 已取消

```
/pick/checkOne
```

参数

```
pickId 预约信息Id：String
checkStatus 预约信息状态：Integer
```

返回值

```json
{
	"code":0,
	"msg":"成功"
}

{
	"code":1,
	"msg":"参数不正确"
}

{
	"code":2,
	"msg":"状态错误"
}
```

## 三、公用的接口

### 1、查询商品

同样的，此接口是用户与管理通用的

#### ① 根据Id查询

```
/product/findById
```

参数

```
productId 商品Id
```

返回值

```json
成功
{
	"code":0,
	"data":{
		"categoryType":3, 商品类型
		"createTime":1558622138000, 创建时间
		"productDescription":"放在油里面煎一下，然后加我在里面做出来的味道才会特别的好。", 商品描述
		"productIcon":"https://s2.ax1x.com/2019/05/23/VC1AiT.png", 商品图片
		"productId":"1558575338537", 商品Id
		"productName":"农家乌鸡", 商品名称
		"productPrice":180.00, 商品单价
		"productStock":100, 商品库存
		"updateTime":1558623073000 最后更新时间
	},
	"msg":"已查到商品信息"
}

失败
{
	"code":1,
	"msg":"未查到商品信息"
}
```

#### ② 根据类别查询

```
/product/findByCategory
```

参数

```
categoryType 商品类型：Integer(0、蔬菜 1、水果 2、花草 3、家禽)
```

返回值

```json
成功
{
	"code":0,
	"data":[
		{
			"categoryType":1,
			"createTime":1558839162000,
			"productDescription":"正宗味道，做法简单，超级好吃！",
			"productIcon":"https://s2.ax1x.com/2019/05/25/VAKWcD.png",
			"productId":"1558792362498",
			"productName":"兰州牛肉面",
			"productPrice":25.50,
			"productStock":200,
			"updateTime":1558842921000
		},
		{
			"categoryType":1,
			"createTime":1558841305000,
			"productDescription":"正宗味道，做法简单，超级好吃！",
			"productIcon":"https://s2.ax1x.com/2019/05/25/VAKWcD.png",
			"productId":"1558793488781",
			"productName":"无锡阳春面",
			"productPrice":25.50,
			"productStock":200,
			"updateTime":1558842924000
		}
	],
	"msg":"已查到该类商品信息"
}

查询结果为空：
{
	"code":1,
	"msg":"无该类商品信息"
}
```



#### ③ 根据名称查询

名词模糊匹配

```
/product/findByName
```

参数

```
productName 商品名称 String
```

返回值

```json
成功
{
	"code":0,
	"data":[
		{
			"categoryType":1,
			"createTime":1558839162000,
			"productDescription":"正宗味道，做法简单，超级好吃！",
			"productIcon":"https://s2.ax1x.com/2019/05/25/VAKWcD.png",
			"productId":"1558792362498",
			"productName":"兰州牛肉面",
			"productPrice":25.50,
			"productStock":200,
			"updateTime":1558842921000
		},
		{
			"categoryType":1,
			"createTime":1558841305000,
			"productDescription":"正宗味道，做法简单，超级好吃！",
			"productIcon":"https://s2.ax1x.com/2019/05/25/VAKWcD.png",
			"productId":"1558793488781",
			"productName":"无锡阳春面",
			"productPrice":25.50,
			"productStock":200,
			"updateTime":1558842924000
		}
	],
	"msg":"已查到符合名称的商品"
}

失败
{
	"code":1,
	"msg":"无符合名称的商品"
}
```



### 2、所有商品展示

此接口同样适用于管理员，商品展示无需权限

```
/product/list
```

参数

```
无
```

返回值

```json
成功
{
	"code":0,
	"data":[
		{
			"categoryType":2,	
			"createTime":1558622138000,
			"productDescription":"优良的观花藤木植物，缠绕茎，羽状复叶，小叶长椭圆形。",
			"productIcon":"https://s2.ax1x.com/2019/05/23/VCMZzd.png",
			"productId":"1558575338397",
			"productName":"紫藤萝",
			"productPrice":35.00,
			"productStock":1024, # 库存
			"updateTime":1558622138000
		},
		{
			"categoryType":2,
			"createTime":1558622138000,
			"productDescription":"叶片线状披针形，顶端长渐尖，基部稍成短鞘。",
			"productIcon":"https://s2.ax1x.com/2019/05/23/VC1PZq.png",
			"productId":"1558575338519",
			"productName":"康乃馨",
			"productPrice":80.00,
			"productStock":500,
			"updateTime":1558623052000
		}
  ],
	"msg":"已经获得全部产品数据"
}
```



### 3、用户数量统计

刘景亮要用

```
/user/userCount
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":10,
	"msg":"统计成功"
}
```



### 4、统计字数

刘景亮要用

```
/user/wordCount/
```

参数

```
word	需要统计的字符串
```

返回值

```json
{
	"code":0,
	"data":10,
	"msg":"统计成功"
}
```

### 5、分类热度

不传参数时和传的时候都返回热度最高的分类信息，传的时候对应分类热度会增加

```
/category/click
```

参数(可不传)

```
category 商品类型：Integer
```

返回值

返回的是热度最高的分类信息

```json
{
	"code":0,
	"data":{
		"categoryId":2,
		"categoryName":"水果",
		"categoryType":11,
		"clickNum":6,
		"createTime":1558620169000,
		"updateTime":1559327462000
	},
	"msg":"Success"
}
```

### 6、发送消息

可能失败的原因：

* 用户未登录
* 发给了Id不存在的用户

```
/msg/send
```

参数

```
msgTo 发给谁（userId）？String
msgMsg 消息内容 String
```

返回值

```json
{
	"code":0,
	"msg":"成功"
}

{
	"code":1,
	"msg":"失败"
}
```

### 7、接收消息

可能失败的原因：

- 用户未登录

```
/msg/accept
```

参数

```
无
```

返回值

```json
成功
{
	"code":0,
	"data":[
		{
			"msgFrom":"00001",	//谁发的
			"msgId":"1559720493767737680",	//消息Id
			"msgMsg":"我是小靓仔，你呢",	//消息内容
			"msgNext":"1559720493767275007", 	//下一条消息Id
			"msgRead":1,	//已读
			"msgTo":"00000"	//发给谁
		},
		{
			"msgFrom":"00001",
			"msgId":"1559720524681222308",
			"msgMsg":"说了我叫小靓仔",
			"msgNext":"1559720639030865974",
			"msgRead":1,
			"msgTo":"00000"
		},
	"msg":"成功"
}

失败
{
	"code":1,
	"msg":"无消息可以接收"
}
```

### 8、查找实践基地

```
/practice/find
```

参数

```
practiceBaseId 实践基地Id：String
```

返回值

```json
{
	"code":0,
	"data":{
		"baseAddress":"陕西省西安市",
		"baseDescription":"创新实践基地是由西安\b某大学学生工作处实施和运作的以学生为中心",
		"baseEnd":1559784323000,
		"baseIcon":"https://s2.ax1x.com/2019/06/06/VdXRk8.png",
		"baseId":"1559837217006752140",
		"baseJoin":4,
		"baseMaxpeople":300,
		"baseName":"西安创新实践基地",
		"baseStart":1559762723000
	},
	"msg":"成功"
}
```

### 9、展示实践基地

```
/practice/showAll
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"baseAddress":"陕西省西安市",
			"baseDescription":"创新实践基地是由西安\b某大学学生工作处实施和运作的以学生为中心",
			"baseEnd":1559784323000,
			"baseIcon":"https://s2.ax1x.com/2019/06/06/VdXRk8.png",
			"baseId":"1559837078331742182",
			"baseJoin":4,
			"baseMaxpeople":200,
			"baseName":"西安创新实践基地",
			"baseStart":1559762723000
		},
		{
			"baseAddress":"陕西省西安市",
			"baseDescription":"创新实践基地是由西安\b某大学学生工作处实施和运作的以学生为中心",
			"baseEnd":1559784323000,
			"baseIcon":"https://s2.ax1x.com/2019/06/06/VdXRk8.png",
			"baseId":"1559837217006752140",
			"baseJoin":4,
			"baseMaxpeople":300,
			"baseName":"西安创新实践基地",
			"baseStart":1559762723000
		}
	],
	"msg":"成功"
}
```



### 10、查找领养植物

```
/botany/find
```

参数

```
botanyId 植物Id：String
```

返回值

```json
{
	"code":0,
	"data":{
		"botanyDescription":"多肉是绿植领养的老朋友啦！它们生命顽强，外形可爱，深受大家欢迎。",
		"botanyIcon":"https://s2.ax1x.com/2019/06/07/V0iEWt.png",
		"botanyId":"1559895146331340768",
		"botanyName":"五彩多肉",
		"botanyNum":200 //库存数量
	},
	"msg":"成功"
}
```

### 11、展示所有可领养植物

```
/botany/showAll
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"botanyDescription":"亚热带和热带常用的观花植物。喜温暖和充足的阳光，不耐寒。",
			"botanyIcon":"https://s2.ax1x.com/2019/06/07/V0iYlV.png",
			"botanyId":"1559887731124394872",
			"botanyName":"美人蕉",
			"botanyNum":98
		},
		{
			"botanyDescription":"多肉是绿植领养的老朋友啦！它们生命顽强，外形可爱，深受大家欢迎。",
			"botanyIcon":"https://s2.ax1x.com/2019/06/07/V0iEWt.png",
			"botanyId":"1559895146331340768",
			"botanyName":"五彩多肉",
			"botanyNum":200
		},
		{
			"botanyDescription":"佛甲草去年一推出就广受好评，因为它真的很好养。",
			"botanyIcon":"https://s2.ax1x.com/2019/06/07/V0i4kd.png",
			"botanyId":"1559895414899956393",
			"botanyName":"佛甲草",
			"botanyNum":50
		},
		{
			"botanyDescription":"露地播种, 当气温20℃以上时种子萌发, 播后10天左右发芽。",
			"botanyIcon":"https://s2.ax1x.com/2019/06/07/V0ivkj.png",
			"botanyId":"1559895555708617407",
			"botanyName":"太阳花",
			"botanyNum":100
		}
	],
	"msg":"成功"
}
```

### 12、展示所有可采摘植物

其实就是展示了商品分类中除了家禽之类的产品

```
/pick/show
```

参数

```
无
```

返回值

```json
{
	"code":0,
	"data":[
		{
			"categoryType":2,
			"createTime":1558622138000,
			"productDescription":"叶片线状披针形，顶端长渐尖，基部稍成短鞘，中脉明显.",
			"productGrade":"4",
			"productIcon":"https://s2.ax1x.com/2019/05/31/VlwDcn.png",
			"productId":"1558575338519",
			"productName":"康乃馨",
			"productPrice":3.50,
			"productStock":500,
			"updateTime":1559599704000
		},
		{
			"categoryType":2,
			"createTime":1558622138000,
			"productDescription":"原产地中海沿岸及小亚细亚一带.",
			"productGrade":"4",
			"productIcon":"https://s2.ax1x.com/2019/05/31/Vlw439.png",
			"productId":"1558575338525",
			"productName":"风信子",
			"productPrice":3.00,
			"productStock":2000,
			"updateTime":1559599706000
		},
		{
			"categoryType":0,
			"createTime":1559333081000,
			"productDescription":"是茄科番茄属中以成熟多汁浆果为产品的草木植物。",
			"productGrade":"1",
			"productIcon":"https://s2.ax1x.com/2019/05/31/Vldwsx.png",
			"productId":"1559286281552",
			"productName":"西红柿",
			"productPrice":2.50,
			"productStock":1000,
			"updateTime":1559599712000
		},
		{
			"categoryType":1,
			"createTime":1559333182000,
			"productDescription":"芭蕉科芭蕉属植物，又指其果实，热带地区广泛种植。",
			"productGrade":"2",
			"productIcon":"https://s2.ax1x.com/2019/05/31/Vlwj9H.png",
			"productId":"1559286382724",
			"productName":"香蕉",
			"productPrice":3.00,
			"productStock":500,
			"updateTime":1559599726000
		},
		{
			"categoryType":1,
			"createTime":1559333645000,
			"productDescription":"通常品种是一种落叶乔木或灌木，极少数品种为常绿.",
			"productGrade":"4",
			"productIcon":"https://s2.ax1x.com/2019/05/31/VlDMhF.png",
			"productId":"1559286845345",
			"productName":"醇香鸭梨",
			"productPrice":1.50,
			"productStock":100,
			"updateTime":1559599728000
		},
		{
			"categoryType":1,
			"createTime":1559333645000,
			"productDescription":"通常品种是一种落叶乔木或灌木，极少数品种为常绿.",
			"productGrade":"3",
			"productIcon":"https://s2.ax1x.com/2019/05/31/VlDMhF.png",
			"productId":"1559286845346",
			"productName":"醇香鸭梨1",
			"productPrice":1.50,
			"productStock":100,
			"updateTime":1559599730000
		},
		{
			"categoryType":1,
			"createTime":1559333645000,
			"productDescription":"通常品种是一种落叶乔木或灌木，极少数品种为常绿.",
			"productGrade":"3",
			"productIcon":"https://s2.ax1x.com/2019/05/31/VlDMhF.png",
			"productId":"1559286845347",
			"productName":"醇香鸭梨2",
			"productPrice":1.50,
			"productStock":100,
			"updateTime":1559599732000
		},
		{
			"categoryType":1,
			"createTime":1559333645000,
			"productDescription":"通常品种是一种落叶乔木或灌木，极少数品种为常绿.",
			"productGrade":"4",
			"productIcon":"https://s2.ax1x.com/2019/05/31/VlDMhF.png",
			"productId":"1559286845348",
			"productName":"醇香鸭梨3",
			"productPrice":1.50,
			"productStock":100,
			"updateTime":1559599734000
		},
		{
			"categoryType":2,
			"createTime":1559742461000,
			"productDescription":"优良的观花藤木植物，缠绕茎，羽状复叶，小叶长椭圆形。",
			"productGrade":"4",
			"productIcon":"https://s2.ax1x.com/2019/05/31/VldOln.png",
			"productId":"1559695661149",
			"productName":"紫藤萝",
			"productPrice":2.00,
			"productStock":1024,
			"updateTime":1559742461000
		},
		{
			"categoryType":2,
			"createTime":1559749135000,
			"productDescription":"菠萝菠萝蜜菠萝菠萝蜜菠萝菠萝蜜菠萝菠萝蜜菠萝菠萝蜜,
			"productGrade":"5",
			"productIcon":"https://i.loli.net/2019/06/05/5cf72b3dbd64753938.png",
			"productId":"1559702335592",
			"productName":"菠萝菠萝蜜",
			"productPrice":2.80,
			"productStock":1024,
			"updateTime":1559749135000
		}
	],
	"msg":"成功"
}
```