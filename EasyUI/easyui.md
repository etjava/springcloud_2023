# Easyui

## 概述

```
jQuery EasyUI 框架提供了创建网页所需的一切，帮助您轻松建立站点。

easyui 是一个基于 jQuery 的框架，集成了各种用户界面插件。
easyui 提供建立现代化的具有交互性的 javascript 应用的必要的功能。
使用 easyui，您不需要写太多 javascript 代码，一般情况下您只需要使用一些 html 标记来定义用户界面。
HTML 网页的完整框架。
easyui 节省了开发产品的时间和规模。
easyui 非常简单，但是功能非常强大。

在线文档
http://doc.yaojieyun.com/www.runoob.com/jeasyui/jqueryeasyui-tutorial.html

```

## 基本组件

### Load组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic EasyLoader - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本EasyLoader组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮动态加载组件.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="load1()">加载日历</a>
		<a href="#" class="easyui-linkbutton" onclick="load2()">加载对话框</a>
		<a href="#" class="easyui-linkbutton" onclick="load3()">加载数据表格</a>
	</div>
	<div id="cc"></div>
	<div id="dd"></div>
	<table id="tt"></table>
	<script type="text/javascript" src="../easyui/easyloader.js"></script>
	<script>
		function load1(){
			using('calendar', function(){
				$('#cc').calendar({
					width:180,
					height:180
				});
			});
		}
		function load2(){
			using(['dialog','messager'], function(){
				$('#dd').dialog({
					title:'对话框',
					width:300,
					height:200
				});
				$.messager.show({
					title:'系统提示',
					msg:'对话框被创建'
				});
			});
		}
		function load3(){
			using('datagrid', function(){
				$('#tt').datagrid({
					title:'数据表格',
					width:300,
					height:200,
					fitColumns:true,
					columns:[[
						{field:'productid',title:'产品编号',width:100},
						{field:'productname',title:'产品名称',width:200}
					]],
					data: [
						{"productid":"FI-SW-01","productname":"Koi"},
						{"productid":"K9-DL-01","productname":"Dalmation"},
						{"productid":"RP-SN-01","productname":"Rattlesnake"},
						{"productid":"RP-LI-02","productname":"Iguana"},
						{"productid":"FL-DSH-01","productname":"Manx"},
						{"productid":"FL-DLH-02","productname":"Persian"},
						{"productid":"AV-CB-01","productname":"Amazon Parrot"}
					]
				});
			});
		}
	</script>

</body>
</html>
```

![image-20231015231341679](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152313095.png)

### 拖拽组件 - 基本

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Draggable - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本拖拽组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击鼠标拖动组件.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-draggable" style="width:200px;height:150px;background:#fafafa;border:1px solid #ccc"></div>
	<div class="easyui-draggable" data-options="handle:'#title'" style="width:200px;height:150px;background:#fafafa;border:1px solid #ccc;margin-top:10px">
		<div id="title" style="padding:5px;background:#ccc;color:#fff">标题</div>
	</div>
</body>
</html>
```

### 拖着组件 - 限制拖拽

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Constrain Draggable - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>拖拽限制</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>拖拽对象只能在它的父容器中拖动.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="position:relative;overflow:hidden;border:1px solid #ccc;width:500px;height:300px">
		<div class="easyui-draggable" data-options="onDrag:onDrag" style="width:100px;height:100px;background:#fafafa;border:1px solid #ccc;">
		</div>
	</div>
	<script>
		function onDrag(e){
			var d = e.data;
			if (d.left < 0){d.left = 0}
			if (d.top < 0){d.top = 0}
			if (d.left + $(d.target).outerWidth() > $(d.parent).width()){
				d.left = $(d.parent).width() - $(d.target).outerWidth();
			}
			if (d.top + $(d.target).outerHeight() > $(d.parent).height()){
				d.top = $(d.parent).height() - $(d.target).outerHeight();
			}
		}
	</script>

</body>
</html>
```

### 拖拽组件 - 购物车

```html
  <!DOCTYPE html>
  <html style="height:100%">
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Building a drag-drop shopping cart - jQuery EasyUI Demo</title>
      <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
<script type="text/javascript" src="../easyui/jquery.min.js"></script>
<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
  </head>
  <body style="height:100%;">
  <h2>购物车</h2>
  <div class="easyui-panel" fit="true" border="false" style="height:100%;overflow:hidden">
      <div class="cart">
          <div class="ctitle">购物车</div>
          <div style="background:#fff">
          <table id="cartcontent" fitColumns="true" style="width1:300px;height:auto;">
              <thead>
                  <tr>
                      <th field="name" width=140>商品名称</th>
                      <th field="quantity" width=60 align="right">数量</th>
                      <th field="price" width=60 align="right">价格</th>
                  </tr>
              </thead>
          </table>
          </div>
          <div class="ctitle" style="position:absolute;bottom:40px">把商品拖到这里添加到购物车</div>
      </div>
      <div class="products">
          <ul>
              <li>
                  <a href="#" class="item">
                      <img src="images/shirt1.gif"/>
                      <div>
                          <p>气球</p>
                          <p>价格:RMB25</p>
                      </div>
                  </a>
              </li>
              <li>
                  <a href="#" class="item">
                      <img src="images/shirt2.gif"/>
                      <div>
                          <p>心情</p>
                          <p>价格:RMB25</p>
                      </div>
                  </a>
              </li>
              <li>
                  <a href="#" class="item">
                      <img src="images/shirt3.gif"/>
                      <div>
                          <p>大象</p>
                          <p>价格:RMB25</p>
                      </div>
                  </a>
              </li>
              <li>
                  <a href="#" class="item">
                      <img src="images/shirt4.gif"/>
                      <div>
                          <p>涂鸦</p>
                          <p>价格:RMB25</p>
                      </div>
                  </a>
              </li>
              <li>
                  <a href="#" class="item">
                      <img src="images/shirt5.gif"/>
                      <div>
                          <p>字母组合</p>
                          <p>价格:RMB25</p>
                      </div>
                  </a>
              </li>
              <li>
                  <a href="#" class="item">
                      <img src="images/shirt6.gif"/>
                      <div>
                          <p>摇滚</p>
                          <p>价格:RMB25</p>
                      </div>
                  </a>
              </li>
          </ul>
      </div>
  </div>

      <style type="text/css">
          .products{
              overflow:auto;
              height:100%;
              background:#fafafa;
          }
          .products ul{
              list-style:none;
              margin:0;
              padding:0px;
          }
          .products li{
              display:inline;
              float:left;
              margin:10px;
          }
          .item{
              display:block;
              text-decoration:none;
          }
          .item img{
              border:1px solid #333;
          }
          .item p{
              margin:0;
              font-weight:bold;
              text-align:center;
              color:#c3c3c3;
          }
          .cart{
              float:right;
              position:relative;
              width:260px;
              height:100%;
              background:#ccc;
              padding:0px 10px;
          }
          .ctitle{
              text-align:center;
              color:#555;
              font-size:18px;
              padding:10px;
          }
      </style>
      <script>
          $(function(){
              $('#cartcontent').datagrid({
                  singleSelect:true,
                  showFooter:true
              });
              $('.item').draggable({
                  revert:true,
                  proxy:'clone',
                  onStartDrag:function(){
                      $(this).draggable('options').cursor = 'not-allowed';
                      $(this).draggable('proxy').css('z-index',10);
                  },
                  onStopDrag:function(){
                      $(this).draggable('options').cursor='move';
                  }
              });
              $('.cart').droppable({
                  onDragEnter:function(e,source){
                      $(source).draggable('options').cursor='auto';
                  },
                  onDragLeave:function(e,source){
                      $(source).draggable('options').cursor='not-allowed';
                  },
                  onDrop:function(e,source){
                      var name = $(source).find('p:eq(0)').html();
                      var price = $(source).find('p:eq(1)').html();
                      addProduct(name, parseFloat(price.split('RMB')[1]));
                  }
              });
          });

          function addProduct(name,price){
              var dg = $('#cartcontent');
              var data = dg.datagrid('getData');
              function add(){
                  for(var i=0; i<data.total; i++){
                      var row = data.rows[i];
                      if (row.name == name){
                          row.quantity += 1;
                          return;
                      }
                  }
                  data.total += 1;
                  data.rows.push({
                      name:name,
                      quantity:1,
                      price:price
                  });
              }
              add();
              dg.datagrid('loadData', data);
              var cost = 0;
              var rows = dg.datagrid('getRows');
              for(var i=0; i<rows.length; i++){
                  cost += rows[i].price*rows[i].quantity;
              }
              dg.datagrid('reloadFooter', [{name:'总计',price:cost}]);
          }
      </script>
  </body>
  </html>
```

### 拖拽组件 - 课表

```html
  <!DOCTYPE html>
  <html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Creating a School Timetable - jQuery EasyUI Demo</title>
      <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
<script type="text/javascript" src="../easyui/jquery.min.js"></script>
<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
  </head>
  <body>
      <h2>创建一个学校课程表</h2>
      <div class="demo-info" style="margin-bottom:10px">
          <div class="demo-tip icon-tip">&nbsp;</div>
          <div>点击拖一个课程到课程表里.</div>
      </div>

      <div style="width:700px;">
          <div class="left">
              <table>
                  <tr>
                      <td><div class="item">英语</div></td>
                  </tr>
                  <tr>
                      <td><div class="item">科学</div></td>
                  </tr>
                  <tr>
                      <td><div class="item">音乐</div></td>
                  </tr>
                  <tr>
                      <td><div class="item">历史</div></td>
                  </tr>
                  <tr>
                      <td><div class="item">计算机</div></td>
                  </tr>
                  <tr>
                      <td><div class="item">数学</div></td>
                  </tr>
                  <tr>
                      <td><div class="item">艺术</div></td>
                  </tr>
                  <tr>
                      <td><div class="item">伦理学</div></td>
                  </tr>
              </table>
          </div>
          <div class="right">
              <table>
                  <tr>
                      <td class="blank"></td>
                      <td class="title">星期一</td>
                      <td class="title">星期二</td>
                      <td class="title">星期三</td>
                      <td class="title">星期四</td>
                      <td class="title">星期五</td>
                  </tr>
                  <tr>
                      <td class="time">08:00</td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                  </tr>
                  <tr>
                      <td class="time">09:00</td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                  </tr>
                  <tr>
                      <td class="time">10:00</td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                  </tr>
                  <tr>
                      <td class="time">11:00</td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                  </tr>
                  <tr>
                      <td class="time">12:00</td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                  </tr>
                  <tr>
                      <td class="time">13:00</td>
                      <td class="lunch" colspan="5">午餐时间</td>
                  </tr>
                  <tr>
                      <td class="time">14:00</td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                  </tr>
                  <tr>
                      <td class="time">15:00</td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                  </tr>
                  <tr>
                      <td class="time">16:00</td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                      <td class="drop"></td>
                  </tr>
              </table>
          </div>
      </div>
      <style type="text/css">
          .left{
              width:120px;
              float:left;
          }
          .left table{
              background:#E0ECFF;
          }
          .left td{
              background:#eee;
          }
          .right{
              float:right;
              width:570px;
          }
          .right table{
              background:#E0ECFF;
              width:100%;
          }
          .right td{
              background:#fafafa;
              color:#444;
              text-align:center;
              padding:2px;
          }
          .right td{
              background:#E0ECFF;
          }
          .right td.drop{
              background:#fafafa;
              width:100px;
          }
          .right td.over{
              background:#FBEC88;
          }
          .item{
              text-align:center;
              border:1px solid #499B33;
              background:#fafafa;
              color:#444;
              width:100px;
          }
          .assigned{
              border:1px solid #BC2A4D;
          }

      </style>
      <script>
          $(function(){
              $('.left .item').draggable({
                  revert:true,
                  proxy:'clone'
              });
              $('.right td.drop').droppable({
                  onDragEnter:function(){
                      $(this).addClass('over');
                  },
                  onDragLeave:function(){
                      $(this).removeClass('over');
                  },
                  onDrop:function(e,source){
                      $(this).removeClass('over');
                      if ($(source).hasClass('assigned')){
                          $(this).append(source);
                      } else {
                          var c = $(source).clone().addClass('assigned');
                          $(this).empty().append(c);
                          c.draggable({
                              revert:true
                          });
                      }
                  }
              });
          });
      </script>
  </body>
  </html>
```

拖拽组件 - 容器

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Droppable - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本可拽入容器</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>把左边项拖拽到右边目标区域.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="float:left;width:200px;margin-right:20px;">
		<div class="title">源</div>
		<div>
			<div class="dragitem">苹果</div>
			<div class="dragitem">桃子</div>
			<div class="dragitem">桔子</div>
		</div>
	</div>
	<div style="float:left;width:200px;">
		<div class="title">目标</div>
		<div class="easyui-droppable targetarea"
				data-options="
					accept: '.dragitem',
					onDragEnter:function(e,source){
						$(this).html('进入');
					},
					onDragLeave: function(e,source){
						$(this).html('离开');
					},
					onDrop: function(e,source){
						$(this).html($(source).html() + ' 拽入');
					}
				">
		</div>
	</div>
	<div style="clear:both"></div>
	<style type="text/css">
		.title{
			margin-bottom:10px;
		}
		.dragitem{
			border:1px solid #ccc;
			width:50px;
			height:50px;
			margin-bottom:10px;
		}
		.targetarea{
			border:1px solid red;
			height:150px;
		}
		.proxy{
			border:1px solid #ccc;
			width:80px;
			background:#fafafa;
		}
	</style>
	<script>
		$(function(){
			$('.dragitem').draggable({
				revert:true,
				deltaX:10,
				deltaY:10,
				proxy:function(source){
					var n = $('<div class="proxy"></div>');
					n.html($(source).html()).appendTo('body');
					return n;
				}
			});
		});
	</script>
</body>
</html>
```

### 拖拽组件 - 更改顺序

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Change Items Order - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>改变托动物顺序</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>拖动托动物改变顺序.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<ul style="margin:0;padding:0;margin-left:10px;">
		<li class="drag-item">托动物 1</li>
		<li class="drag-item">托动物2</li>
		<li class="drag-item">托动物3</li>
		<li class="drag-item">托动物4</li>
		<li class="drag-item">托动物5</li>
		<li class="drag-item">托动物6</li>
	</ul>
	<style type="text/css">
		.drag-item{
			list-style-type:none;
			display:block;
			padding:5px;
			border:1px solid #ccc;
			margin:2px;
			width:300px;
			background:#fafafa;
			color:#444;
		}
		.indicator{
			position:absolute;
			font-size:9px;
			width:10px;
			height:10px;
			display:none;
			color:red;
		}
	</style>
	<script>
		$(function(){
			var indicator = $('<div class="indicator">>></div>').appendTo('body');
			$('.drag-item').draggable({
				revert:true,
				deltaX:0,
				deltaY:0
			}).droppable({
				onDragOver:function(e,source){
					indicator.css({
						display:'block',
						left:$(this).offset().left-10,
						top:$(this).offset().top+$(this).outerHeight()-5
					});
				},
				onDragLeave:function(e,source){
					indicator.hide();
				},
				onDrop:function(e,source){
					$(source).insertAfter(this);
					indicator.hide();
				}
			});
		});
	</script>

</body>
</html>
```

### 拖拽组件 - 元素拖拽

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Accept a Drop - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>接受一个拖拽物</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>不接受一些拖拽物.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div id="source" style="border:1px solid #ccc;width:300px;height:400px;float:left;margin:5px;">
		来拖我!
		<div id="d1" class="drag">拖拽物1</div>
		<div id="d2" class="drag">拖拽物2</div>
		<div id="d3" class="drag">拖拽物3</div>
	</div>
	<div id="target" style="border:1px solid #ccc;width:300px;height:400px;float:left;margin:5px;">
		拖到这里！
	</div>
	<div style="clear:both"></div>
	<style type="text/css">
		.drag{
			width:100px;
			height:50px;
			padding:10px;
			margin:5px;
			border:1px solid #ccc;
			background:#AACCFF;
		}
		.dp{
			opacity:0.5;
			filter:alpha(opacity=50);
		}
		.over{
			background:#FBEC88;
		}
	</style>
	<script>
		$(function(){
			$('.drag').draggable({
				proxy:'clone',
				revert:true,
				cursor:'auto',
				onStartDrag:function(){
					$(this).draggable('options').cursor='not-allowed';
					$(this).draggable('proxy').addClass('dp');
				},
				onStopDrag:function(){
					$(this).draggable('options').cursor='auto';
				}
			});
			$('#target').droppable({
				accept:'#d1,#d3',
				onDragEnter:function(e,source){
					$(source).draggable('options').cursor='auto';
					$(source).draggable('proxy').css('border','1px solid red');
					$(this).addClass('over');
				},
				onDragLeave:function(e,source){
					$(source).draggable('options').cursor='not-allowed';
					$(source).draggable('proxy').css('border','1px solid #ccc');
					$(this).removeClass('over');
				},
				onDrop:function(e,source){
					$(this).append(source)
					$(this).removeClass('over');
				}
			});
		});
	</script>

</body>
</html>
```

### 拖拽组件 - 缩放到容器

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Resizable - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本可缩放组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击组件边缘调整组件大小.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-resizable" data-options="minWidth:50,minHeight:50" style="width:200px;height:150px;border:1px solid #ccc;">
		<div style="padding:20px">缩放我</div>
	</div>
	<div id="dd" class="easyui-draggable easyui-resizable" data-options="handle:'#mytitle'" style="width:200px;height:150px;border:1px solid #ccc">
		<div id="mytitle" style="background:#ddd;padding:5px;">标题</div>
		<div style="padding:20px">拖拽并缩放我</div>
	</div>
</body>
</html>
```

### 分页组件 - 基本

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Pagination - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本分页组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>用户能够在分页工具条上改变页码和每天记录大小.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-pagination" data-options="total:114" style="border:1px solid #ddd;"></div>
</body>
</html>
```



### 分页组件 - 定制

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom Pagination Buttons - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>定制分页组件按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>定制按钮能够添加到分页工具条上去.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-pagination" data-options="total:114,buttons:buttons" style="border:1px solid #ddd;"></div>
	<script>
		var buttons = [{
			iconCls:'icon-add',
			handler:function(){
				alert('添加');
			}
		},{
			iconCls:'icon-cut',
			handler:function(){
				alert('剪切');
			}
		},{
			iconCls:'icon-save',
			handler:function(){
				alert('保存');
			}
		}];
	</script>
</body>
</html>
```



### 分页组件 - 简化

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Simplify Pagination - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>简化分页组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>此示例教大家如何简化分页组件.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-pagination" style="border:1px solid #ddd;" data-options="
				total: 114,
				showPageList: false,
				showRefresh: false,
				displayMsg: ''
			"></div>

</body>
</html>
```



### 搜索框组件 - 基本

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic SearchBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本搜索框组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>在输入框中点击搜索按钮或者按Enter键执行查询.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-searchbox" data-options="prompt:'请输入...',searcher:doSearch" style="width:300px"></input>
	<script>
		function doSearch(value){
			alert('您输入的是: ' + value);
		}
	</script>
</body>
</html>
```



### 搜索框组件 - 带分类

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Search Category - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>分类查询</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>选择一个类别然后在输入框中点击搜索按钮或者按Enter键执行查询.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-searchbox" data-options="prompt:'请输入...',menu:'#mm',searcher:doSearch" style="width:300px"></input>
	<div id="mm" style="width:120px">
		<div data-options="name:'all',iconCls:'icon-ok'">所有新闻</div>
		<div data-options="name:'sports'">体育新闻</div>
	</div>
	<script>
		function doSearch(value,name){
			alert('您输入的是: ' + value+'('+name+')');
		}
	</script>

</body>
</html>
```



进度条组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic ProgressBar - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本进度条组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮来显示进度条.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="start()">开始</a>
	</div>
	<div id="p" class="easyui-progressbar" style="width:400px;"></div>
	<script>
		function start(){
			var value = $('#p').progressbar('getValue');
			if (value < 100){
				value += Math.floor(Math.random() * 10);
				$('#p').progressbar('setValue', value);
				setTimeout(arguments.callee, 200);
			}
		};
	</script>
</body>
</html>
```



### 提示信息组件 - 连接

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Tooltip - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本提示信息组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>悬停在超链接上显示提示信息.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<p>能够用每个元素的标题属性作为提示信息.
	<a href="#" title="这是提示信息." class="easyui-tooltip">到我这来</a> 显示提示信息.
	</p>
</body>
</html>
```



### 提示信息组件 - 可选位置

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tooltip Position - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>提示信息位置</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面的下拉框改变提示信息的显示位置.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<span>选择位置:</span>
	<select onchange="changePosition(this.value)">
		<option value="bottom">下</option>
		<option value="top">上</option>
		<option value="left">左</option>
		<option value="right">右</option>
	</select>
	<div style="padding:10px 200px">
	<div id="pp" class="easyui-panel easyui-tooltip" title="这是提示信息." style="width:100px;padding:5px">到我这来</div>
	</div>
	<script type="text/javascript">
		function changePosition(pos){
			$('#pp').tooltip({
				position: pos
			});
		}
	</script>
</body>
</html>
```



### 定制提示信息内容

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom Tooltip Content - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>定制提示信息内容</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>访问每个元素描述获取提示信息内容.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div id="pg" data-options="total:114" style="border:1px solid #ddd;"></div>

	<script>
		$(function(){
			$('#pg').pagination().find('a.l-btn').tooltip({
				content: function(){
					var cc = $(this).find('span.l-btn-empty').attr('class').split(' ');
					var icon = cc[1].split('-')[1];
					return icon + ' page';
				}
			});
		});
	</script>
</body>
</html>
```



### 定制提示信息组件  - 不同风格

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom Tooltip Style - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>定制提示信息风格</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教我们如何定制提示信息的风格.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:10px 200px">
		<div id="pp1" class="easyui-panel" style="width:100px;padding:5px">搞我啊！</div>
	</div>
	<div style="padding:10px 200px">
		<div id="pp2" class="easyui-panel" style="width:100px;padding:5px">搞我呀！</div>
	</div>
	<script>
		$(function(){
			$('#pp1').tooltip({
				position: 'right',
				content: '<span style="color:#fff">这是一种风格的提示信息.</span>',
				onShow: function(){
					$(this).tooltip('tip').css({
						backgroundColor: '#666',
						borderColor: '#666'
					});
				}
			});
			$('#pp2').tooltip({
				position: 'bottom',
				content: '<div style="padding:5px;background:#eee;color:#000">这又是一种风格的提示信息.</div>',
				onShow: function(){
					$(this).tooltip('tip').css({
						backgroundColor: '#fff000',
						borderColor: '#ff0000',
						boxShadow: '1px 1px 3px #292929'
					});
				},
				onPosition: function(){
					$(this).tooltip('tip').css('left', $(this).offset().left);
					$(this).tooltip('arrow').css('left', 20);
				}
			});
		});
	</script>
</body>
</html>
```



### 定制提示信息组件 - 工具条

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tooltip as Toolbar - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>提示信息组件作为工具条</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教大家如果创建一个提示信息风格的工具条.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:10px 200px">
		<p><a id="dd" href="javascript:void(0)" class="easyui-tooltip" data-options="
					hideEvent: 'none',
					content: function(){
						return $('#toolbar');
					},
					onShow: function(){
						var t = $(this);
						t.tooltip('tip').focus().unbind().bind('blur',function(){
							t.tooltip('hide');
						});
					}
				">到我这来</a> 搞出一个工具条.</p>
	</div>
	<div style="display:none">
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton easyui-tooltip" title="添加" data-options="iconCls:'icon-add',plain:true"></a>
			<a href="#" class="easyui-linkbutton easyui-tooltip" title="剪切" data-options="iconCls:'icon-cut',plain:true"></a>
			<a href="#" class="easyui-linkbutton easyui-tooltip" title="删除" data-options="iconCls:'icon-remove',plain:true"></a>
			<a href="#" class="easyui-linkbutton easyui-tooltip" title="撤销" data-options="iconCls:'icon-undo',plain:true"></a>
			<a href="#" class="easyui-linkbutton easyui-tooltip" title="恢复" data-options="iconCls:'icon-redo',plain:true"></a>
		</div>
	</div>
</body>
</html>
```



### Ajax提示信息

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Ajax Tooltip - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>Ajax提示信息</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>提示信息可以通过AJAX加载.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<a href="#" class="easyui-tooltip" data-options="
			content: $('<div></div>'),
			onShow: function(){
				$(this).tooltip('arrow').css('left', 20);
				$(this).tooltip('tip').css('left', $(this).offset().left);
			},
			onUpdate: function(cc){
				cc.panel({
					width: 500,
					height: 'auto',
					border: false,
					href: '../tooltip/_content.html'
				});
			}
		">到我这来</a> 显示AJAX加载的提示信息.
</body>
</html>
```



### 提示信息组件 - 对话框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tooltip Dialog - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>提示信息对话框</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教我们如何创建一个提示信息对话框.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:10px 200px">
		<p><a id="dd" href="javascript:void(0)">点这里</a> 来显示一个提示信息对话框.
	</div>
	<script>
		$(function(){
			$('#dd').tooltip({
				content: $('<div></div>'),
				showEvent: 'click',
				onUpdate: function(content){
					content.panel({
						width: 200,
						border: false,
						title: '登录',
						href: '../tooltip/_dialog.html'
					});
				},
				onShow: function(){
					var t = $(this);
					t.tooltip('tip').unbind().bind('mouseenter', function(){
						t.tooltip('show');
					}).bind('mouseleave', function(){
						t.tooltip('hide');
					});
				}
			});
		});
	</script>
</body>
</html>
```



## 菜单和按钮

### 基本菜单组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Menu - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本菜单组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>在页面上右击显示菜单.</div>
	</div>
	<div style="margin:10px 0;"></div>

	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="javascript:alert('新建')">新建</div>
		<div>
			<span>打开</span>
			<div style="width:150px;">
				<div><b>Word</b></div>
				<div>Excel</div>
				<div>PowerPoint</div>
				<div>
					<span>M1</span>
					<div style="width:120px;">
						<div>sub1</div>
						<div>sub2</div>
						<div>
							<span>Sub</span>
							<div style="width:80px;">
								<div onclick="javascript:alert('sub21')">sub21</div>
								<div>sub22</div>
								<div>sub23</div>
							</div>
						</div>
						<div>sub3</div>
					</div>
				</div>
				<div>
					<span>Window Demos</span>
					<div style="width:120px;">
						<div data-options="href:'window.html'">Window</div>
						<div data-options="href:'dialog.html'">Dialog</div>
						<div><a href="http://www.jeasyui.com" target="_blank">EasyUI</a></div>
					</div>
				</div>
			</div>
		</div>
		<div data-options="iconCls:'icon-save'">保存</div>
		<div data-options="iconCls:'icon-print',disabled:true">打印</div>
		<div class="menu-sep"></div>
		<div>退出</div>
	</div>
	<script>
		$(function(){
			$(document).bind('contextmenu',function(e){
				e.preventDefault();
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			});
		});
	</script>
</body>
</html>
```



### 定制菜单组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom Menu Item - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>定制菜单项</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>在页面上右击显示菜单，移到“打开”菜单项显示它的定制内容.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div>新建</div>
		<div>
			<span>打开</span>
			<div class="menu-content" style="text-align:left;padding:10px">
				<div style="font-weight:bold;font-size:16px">选择一种语言:</div>
				<ul style="margin:0;padding:0 0 0 40px">
					<li><a href="javascript:void(0)">Java</a></li>
					<li><a href="javascript:void(0)">Basic</a></li>
					<li><a href="javascript:void(0)">C++</a></li>
					<li><a href="javascript:void(0)">Fortran</a></li>
					<li>
						<span>其他</span>
						<input>
					</li>
				</ul>
				<div style="padding:10px 0 0 30px">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a>
				</div>
			</div>
		</div>
		<div data-options="iconCls:'icon-save'">保存</div>
		<div data-options="iconCls:'icon-print'">打印</div>
		<div class="menu-sep"></div>
		<div>退出</div>
	</div>
	<script>
		$(function(){
			$(document).bind('contextmenu',function(e){
				e.preventDefault();
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			});
		});
	</script>

</body>
</html>
```



### 菜单事件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Menu Events - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>菜单事件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>在页面上右击点击菜单项.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width:120px;">
		<div data-options="name:'new'">新建</div>
		<div data-options="name:'save',iconCls:'icon-save'">保存</div>
		<div data-options="name:'print',iconCls:'icon-print'">打印</div>
		<div class="menu-sep"></div>
		<div data-options="name:'exit'">退出</div>
	</div>
	<script>
		function menuHandler(item){
			alert(item.name);
		}
		$(function(){
			$(document).bind('contextmenu',function(e){
				e.preventDefault();
				$('#mm').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			});
		});
	</script>
</body>
</html>
```



### 基本连接按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic LinkButton - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本链接按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>按钮能够通过 &lt;a/&gt; 标签创建.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:5px;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cut',disabled:true">剪切</a>
		<a href="#" class="easyui-linkbutton">文本按钮</a>
	</div>

</body>
</html>
```



### 普通连接按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Plain LinkButton - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>普通链接按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>链接按钮只有普通效果.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:5px;border:1px solid #ddd;">
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">关闭</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">搜索</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true">文本按钮</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-print'">打印</a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-help'"> </a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'"></a>
		<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'"></a>
	</div>

</body>
</html>
```



### 连接按钮图标位置

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Icon Align on LinkButton - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>链接按钮的图标对齐位置</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>选择图标对齐方式，重置按钮图标左对齐或者右对齐.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div id="button-bar">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cut',disabled:true">剪切</a>
	</div>
	<div style="margin:10px 0">
		<span>选择图标对齐位置: </span>
		<select onchange="$('#button-bar a').linkbutton({iconAlign:this.value})">
			<option value="left">左</option>
			<option value="right">右</option>
		</select>
	</div>
</body>
</html>
```



### 开关按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Toggle Button - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>开关按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮来切换它的选中选中状态.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:5px;">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',toggle:true">添加</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',toggle:true">删除</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',toggle:true">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cut',toggle:true,disabled:true">剪切</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true">文本按钮</a>
	</div>

</body>
</html>
```



### 按钮组

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Button Group - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>按钮组</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>在一个按钮组中只能有一个按钮被选中.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:5px;border:1px solid #ddd;">
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">按钮1</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">按钮2</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">按钮3</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">按钮4</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g1'">按钮5</a>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:5px;border:1px solid #ddd;">
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2',plain:true">按钮1</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2',plain:true">按钮2</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2',plain:true">按钮3</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2',plain:true">按钮4</a>
		<a href="#" class="easyui-linkbutton" data-options="toggle:true,group:'g2',plain:true">按钮5</a>
	</div>

</body>
</html>
```



### 基本菜单按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic MenuButton - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本菜单按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>鼠标移到菜单按钮上显示下拉菜单.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:5px;border:1px solid #ddd">
		<a href="#" class="easyui-linkbutton" data-options="plain:true">主页</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">编辑</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'icon-help'">帮助</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm3'">关于</a>
	</div>
	<div id="mm1" style="width:150px;">
		<div data-options="iconCls:'icon-undo'">撤销</div>
		<div data-options="iconCls:'icon-redo'">恢复</div>
		<div class="menu-sep"></div>
		<div>剪切</div>
		<div>复制</div>
		<div>粘贴</div>
		<div class="menu-sep"></div>
		<div>
			<span>工具条</span>
			<div style="width:150px;">
				<div>地址</div>
				<div>链接</div>
				<div>导航工具条</div>
				<div>书签工具条</div>
				<div class="menu-sep"></div>
				<div>新建工具条...</div>
			</div>
		</div>
		<div data-options="iconCls:'icon-remove'">删除</div>
		<div>全选</div>
	</div>
	<div id="mm2" style="width:100px;">
		<div>帮助</div>
		<div>更新</div>
		<div>关于</div>
	</div>
	<div id="mm3" class="menu-content" style="background:#f0f0f0;padding:10px;text-align:left">
		<img src="http://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">
		<p style="font-size:14px;color:#444;">EasyUI就是好.</p>
	</div>
</body>
</html>
```



### 带禁用功能的菜单按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>MenuButton Actions - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>菜单按钮相关操作</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮执行相关操作.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="$('#btn-edit').menubutton('disable')">禁用编辑</a>
		<a href="#" class="easyui-linkbutton" onclick="$('#btn-edit').menubutton('enable')">启用编辑</a>
	</div>
	<div style="padding:5px;border:1px solid #ddd">
		<a href="#" class="easyui-linkbutton" data-options="plain:true">主页</a>
		<a id="btn-edit" href="#" class="easyui-menubutton" data-options="menu:'#mm1',iconCls:'icon-edit'">编辑</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm2',iconCls:'icon-help'">帮助</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm3'">关于</a>
	</div>
	<div id="mm1" style="width:150px;">
		<div data-options="iconCls:'icon-undo'">撤销</div>
		<div data-options="iconCls:'icon-redo'">恢复</div>
		<div class="menu-sep"></div>
		<div>剪切</div>
		<div>复制</div>
		<div>粘贴</div>
		<div class="menu-sep"></div>
		<div>
			<span>工具条</span>
			<div style="width:150px;">
				<div>地址</div>
				<div>链接</div>
				<div>导航工具条</div>
				<div>书签工具条</div>
				<div class="menu-sep"></div>
				<div>新建工具条...</div>
			</div>
		</div>
		<div data-options="iconCls:'icon-remove'">删除</div>
		<div>全选</div>
	</div>
	<div id="mm2" style="width:100px;">
		<div>帮助</div>
		<div>更新</div>
		<div>关于</div>
	</div>
	<div id="mm3" class="menu-content" style="background:#f0f0f0;padding:10px;text-align:left">
		<img src="http://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">
		<p style="font-size:14px;color:#444;">EasyUI就是好.</p>
	</div>

</body>
</html>
```



### 带分割线的菜单按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic SplitButton - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本分隔按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>把鼠标移到按钮的箭头区域来显示下拉菜单.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:5px;border:1px solid #ddd;">
		<a href="#" class="easyui-linkbutton" data-options="plain:true">主页</a>
		<a href="#" class="easyui-splitbutton" data-options="menu:'#mm1',iconCls:'icon-edit'">编辑</a>
		<a href="#" class="easyui-splitbutton" data-options="menu:'#mm2',iconCls:'icon-ok'">确定</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm3',iconCls:'icon-help'">帮助</a>
	</div>
	<div id="mm1" style="width:150px;">
		<div data-options="iconCls:'icon-undo'">撤销</div>
		<div data-options="iconCls:'icon-redo'">恢复</div>
		<div class="menu-sep"></div>
		<div>剪切</div>
		<div>复制</div>
		<div>粘贴</div>
		<div class="menu-sep"></div>
		<div>
			<span>工具条</span>
			<div style="width:150px;">
				<div>地址</div>
				<div>链接</div>
				<div>导航工具条</div>
				<div>书签工具条</div>
				<div class="menu-sep"></div>
				<div>新建工具条...</div>
			</div>
		</div>
		<div data-options="iconCls:'icon-remove'">删除</div>
		<div>全选</div>
	</div>
	<div id="mm2" style="width:100px;">
		<div data-options="iconCls:'icon-ok'">确定</div>
		<div data-options="iconCls:'icon-cancel'">关闭</div>
	</div>
	<div id="mm3" style="width:150px;">
		<div>帮助</div>
		<div>更新</div>
		<div>
			<span>关于</span>
			<div class="menu-content" style="padding:10px;text-align:left">
				<img src="http://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">
				<p style="font-size:14px;color:#444">EasyUI就是好.</p>
			</div>
		</div>
	</div>

</body>
</html>
```



### 带禁用功能的分隔线菜单按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic SplitButton - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本分隔按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>把鼠标移到按钮的箭头区域来显示下拉菜单.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="padding:5px;border:1px solid #ddd;">
		<a href="#" class="easyui-linkbutton" data-options="plain:true">主页</a>
		<a href="#" class="easyui-splitbutton" data-options="menu:'#mm1',iconCls:'icon-edit'">编辑</a>
		<a href="#" class="easyui-splitbutton" data-options="menu:'#mm2',iconCls:'icon-ok'">确定</a>
		<a href="#" class="easyui-menubutton" data-options="menu:'#mm3',iconCls:'icon-help'">帮助</a>
	</div>
	<div id="mm1" style="width:150px;">
		<div data-options="iconCls:'icon-undo'">撤销</div>
		<div data-options="iconCls:'icon-redo'">恢复</div>
		<div class="menu-sep"></div>
		<div>剪切</div>
		<div>复制</div>
		<div>粘贴</div>
		<div class="menu-sep"></div>
		<div>
			<span>工具条</span>
			<div style="width:150px;">
				<div>地址</div>
				<div>链接</div>
				<div>导航工具条</div>
				<div>书签工具条</div>
				<div class="menu-sep"></div>
				<div>新建工具条...</div>
			</div>
		</div>
		<div data-options="iconCls:'icon-remove'">删除</div>
		<div>全选</div>
	</div>
	<div id="mm2" style="width:100px;">
		<div data-options="iconCls:'icon-ok'">确定</div>
		<div data-options="iconCls:'icon-cancel'">关闭</div>
	</div>
	<div id="mm3" style="width:150px;">
		<div>帮助</div>
		<div>更新</div>
		<div>
			<span>关于</span>
			<div class="menu-content" style="padding:10px;text-align:left">
				<img src="http://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">
				<p style="font-size:14px;color:#444">EasyUI就是好.</p>
			</div>
		</div>
	</div>

</body>
</html>
```

![image-20231015210353364](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152104156.png)

## 数据表格

### 数据表格 - 基本

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本数据表格</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>数据表格使用标签创建，不需要JavaScript代码.</div>
	</div>
	<div style="margin:10px 0;"></div>

	<table class="easyui-datagrid" title="基本数据表格" style="width:700px;height:250px"
			data-options="singleSelect:true,collapsible:true,url:'../datagrid/datagrid_data1.json'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

![image-20231015210531449](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152105467.png)

### 数据表格 - 可多选

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Transform DataGrid from Table - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>把Table转化成数据表格</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>把Table转化成数据表格.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" style="width:500px;height:auto">
		<thead>
			<tr>
				<th data-options="field:'itemid'">编号</th>
				<th data-options="field:'productid'">产品编号</th>
				<th data-options="field:'listprice',align:'right'">市场价</th>
				<th data-options="field:'attr1'">描述</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>EST-1</td><td>FI-SW-01</td><td>36.50</td><td>Large</td>
			</tr>
			<tr>
				<td>EST-10</td><td>K9-DL-01</td><td>18.50</td><td>Spotted Adult Female</td>
			</tr>
			<tr>
				<td>EST-11</td><td>RP-SN-01</td><td>28.50</td><td>Venomless</td>
			</tr>
			<tr>
				<td>EST-12</td><td>RP-SN-01</td><td>26.50</td><td>Rattleless</td>
			</tr>
			<tr>
				<td>EST-13</td><td>RP-LI-02</td><td>35.50</td><td>Green Adult</td>
			</tr>
		</tbody>
	</table>
</body>
</html>
```

![image-20231015210633866](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152106861.png)

### 数据表格样式

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Row Border in DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格中的行边框</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教我们如何改变表格的行边框样式.</div>
	</div>
	<div style="margin:10px 0;">
		<span>边框:</span>
		<select onchange="changeBorder(this.value)">
			<option value="lines-both">都有</option>
			<option value="lines-no">无边框</option>
			<option value="lines-right">右边框</option>
			<option value="lines-bottom">下边框</option>
		</select>
		<span>斑马线:</span>
		<input type="checkbox" onclick="$('#dg').datagrid({striped:$(this).is(':checked')})">
	</div>
	<table id="dg" class="easyui-datagrid" title="数据表格中的行边框" style="width:705px;height:250px"
			data-options="singleSelect:true,fitColumns:true,url:'../datagrid/datagrid_data1.json'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		function changeBorder(cls){
			$('#dg').datagrid('getPanel').removeClass('lines-both lines-no lines-right lines-bottom').addClass(cls);
		}
	</script>
	<style type="text/css">
		.lines-both .datagrid-body td{
		}
		.lines-no .datagrid-body td{
			border-right:1px dotted transparent;
			border-bottom:1px dotted transparent;
		}
		.lines-right .datagrid-body td{
			border-bottom:1px dotted transparent;
		}
		.lines-bottom .datagrid-body td{
			border-right:1px dotted transparent;
		}
	</style>

</body>
</html>
```

![image-20231015210708878](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152107988.png)

### 数据表格 - 获取选中数据

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DataGrid Selection - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格选择模型</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>选择一种选择模型并且选择一行或者多行.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="getSelected()">获取单选数据</a>
		<a href="#" class="easyui-linkbutton" onclick="getSelections()">获取多选数据</a>
	</div>
	<table id="dg" class="easyui-datagrid" title="数据表格选择模型" style="width:705px;height:250px"
			data-options="singleSelect:true,url:'../datagrid/datagrid_data1.json'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<div style="margin:10px 0;">
		<span>选择模型: </span>
		<select onchange="$('#dg').datagrid({singleSelect:(this.value==0)})">
			<option value="0">单选</option>
			<option value="1">多选</option>
		</select>
	</div>
	<script type="text/javascript">
		function getSelected(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.alert('系统提示', row.itemid+":"+row.productid+":"+row.attr1);
			}
		}
		function getSelections(){
			var ss = [];
			var rows = $('#dg').datagrid('getSelections');
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				ss.push('<span>'+row.itemid+":"+row.productid+":"+row.attr1+'</span>');
			}
			$.messager.alert('系统提示', ss.join('<br/>'));
		}
	</script>
</body>
</html>
```

![image-20231015210802568](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152108614.png)

### 数据表格 - 单选、多选

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CheckBox Selection on DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格复选框选择模型</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击顶部复选框全选或者全不选.</div>
	</div>
	<div style="margin:10px 0;"></div>

	<table id="dg" class="easyui-datagrid" title="数据表格复选框选择模型" style="width:705px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'../datagrid/datagrid_data1.json'">
		<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<div style="margin:10px 0;">
		<span>选择模型: </span>
		<select onchange="$('#dg').datagrid({singleSelect:(this.value==0)})">
			<option value="0">单选</option>
			<option value="1">多选</option>
		</select><br/>
		选择联动复选框: <input type="checkbox" checked onchange="$('#dg').datagrid({selectOnCheck:$(this).is(':checked')})"><br/>
		复选框联动选择: <input type="checkbox" checked onchange="$('#dg').datagrid({checkOnSelect:$(this).is(':checked')})">
	</div>

</body>
</html>
```

![image-20231015210854725](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152108830.png)

### 数据表格 - 带工具条

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DataGrid with Toolbar - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格工具条</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击数据表格顶部工具条上的按钮.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="数据表格工具条" style="width:705px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'../datagrid/datagrid_data1.json',toolbar:toolbar">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		var toolbar = [{
			text:'添加',
			iconCls:'icon-add',
			handler:function(){alert('添加')}
		},{
			text:'剪切',
			iconCls:'icon-cut',
			handler:function(){alert('剪切')}
		},'-',{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){alert('保存')}
		}];
	</script>
</body>
</html>
```

![image-20231015210925457](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152109515.png)

### 数据表格 - 复杂工具条

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DataGrid Complex Toolbar - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格复杂工具条</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>数据表格能够通过div标签来定义, 所以你能够轻松定义你的工具条布局.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="数据表格复杂工具条" style="width:705px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'../datagrid/datagrid_data1.json',toolbar:'#tb'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"></a>
		</div>
		<div>
			日期从: <input class="easyui-datebox" style="width:80px">
			到: <input class="easyui-datebox" style="width:80px">
			语言:
			<select class="easyui-combobox" panelHeight="auto" style="width:100px">
				<option value="java">Java</option>
				<option value="c">C</option>
				<option value="basic">Basic</option>
				<option value="perl">Perl</option>
				<option value="python">Python</option>
			</select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
		</div>
	</div>
</body>
</html>
```

### 数据表格 - 带分页

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom DataGrid Pager - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>定制数据表格分页</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>你能够把一些按钮添加到标准的数据表格分页工具条上去.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table id="dg" class="easyui-datagrid" title="定制数据表格分页" style="width:705px;height:250px"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'../datagrid/datagrid_data1.json'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		$(function(){
			var pager = $('#dg').datagrid('getPager');	// get the pager of datagrid
			pager.pagination({
				buttons:[{
					iconCls:'icon-search',
					handler:function(){
						alert('搜索');
					}
				},{
					iconCls:'icon-add',
					handler:function(){
						alert('添加');
					}
				},{
					iconCls:'icon-edit',
					handler:function(){
						alert('编辑');
					}
				}]
			});
		})
	</script>
</body>
</html>
```

![image-20231015211018572](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152110538.png)

### 数据表格 - 简单分页

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Client Side Pagination in DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格客户端分页</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教我们如何在客户端实现数据表格分页.</div>
	</div>
	<div style="margin:10px 0;"></div>

	<table id="dg" title="客户端分页" style="width:700px;height:300px" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th field="inv" width="80">月份</th>
				<th field="date" width="100">日期</th>
				<th field="name" width="80">姓名</th>
				<th field="amount" width="80" align="right">数量</th>
				<th field="price" width="80" align="right">价格</th>
				<th field="cost" width="100" align="right">费用</th>
				<th field="note" width="110">备注</th>
			</tr>
		</thead>
	</table>
	<script>
		function getData(){
			var rows = [];
			for(var i=1; i<=800; i++){
				var amount = Math.floor(Math.random()*1000);
				var price = Math.floor(Math.random()*1000);
				rows.push({
					inv: i+'月',
					date: $.fn.datebox.defaults.formatter(new Date()),
					name: '姓名 '+i,
					amount: amount,
					price: price,
					cost: amount*price,
					note: '备注 '+i
				});
			}
			return rows;
		}

		function pagerFilter(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
				data = {
					total: data.length,
					rows: data
				}
			}
			var dg = $(this);
			var opts = dg.datagrid('options');
			var pager = dg.datagrid('getPager');
			pager.pagination({
				onSelectPage:function(pageNum, pageSize){
					opts.pageNumber = pageNum;
					opts.pageSize = pageSize;
					pager.pagination('refresh',{
						pageNumber:pageNum,
						pageSize:pageSize
					});
					dg.datagrid('loadData',data);
				}
			});
			if (!data.originalRows){
				data.originalRows = (data.rows);
			}
			var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
			var end = start + parseInt(opts.pageSize);
			data.rows = (data.originalRows.slice(start, end));
			return data;
		}

		$(function(){
			$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', getData());
		});
	</script>
</body>
</html>
```

![image-20231015211105786](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152111821.png)

### 数据表格 - 合并列

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Column Group - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>列组合</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>列头部单元可以合并,能够有效地把一些列归类.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="列组合" style="width:705px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'../datagrid/datagrid_data1.json'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80" rowspan="2">编号</th>
				<th data-options="field:'productid',width:100" rowspan="2">产品编号</th>
				<th colspan="4">产品详细信息</th>
			</tr>
			<tr>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:240">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

![image-20231015211150452](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152111975.png)

### 数据表格 - 列对齐

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Aligning Columns in DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格列对齐</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>使用align和halign来设置列和他们的列头对齐.</div>
	</div>
	<div style="margin:10px 0;"></div>

	<table class="easyui-datagrid" title="数据表格列对齐" style="width:700px;height:250px"
			data-options="singleSelect:true,collapsible:true,url:'../datagrid/datagrid_data1.json'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80,halign:'center'">编号</th>
				<th data-options="field:'productid',width:100,halign:'center'">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right',halign:'center'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right',halign:'center'">成本价</th>
				<th data-options="field:'attr1',width:250,halign:'center'">描述</th>
				<th data-options="field:'status',width:60,align:'center',halign:'center'">状态</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

![image-20231015211224761](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152112298.png)

### 数据表格 - 冻结列

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Frozen Columns in DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格中冻结列</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>你能够冻结一些列.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="数据表格中冻结列" style="width:700px;height:250px"
			data-options="rownumbers:true,singleSelect:true,url:'../datagrid/datagrid_data1.json'">
		<thead data-options="frozen:true">
			<tr>
				<th data-options="field:'itemid',width:100">编号</th>
				<th data-options="field:'productid',width:120">商品编号</th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th data-options="field:'listprice',width:90,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:90,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

### 数据表格 - 格式转换

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Format DataGrid Columns - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>格式化数据表格列</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>市场价小于30的显示红色.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="格式化数据表格列" style="width:700px;height:250px"
			data-options="rownumbers:true,singleSelect:true,iconCls:'icon-ok',url:'../datagrid/datagrid_data1.json'">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right',formatter:formatPrice">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:240">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<script>
		function formatPrice(val,row){
			if (val < 30){
				return '<span style="color:red;">('+val+')</span>';
			} else {
				return val;
			}
		}
	</script>
</body>
</html>
```

![image-20231015211327936](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152113018.png)

### 数据表格 - 冻结行

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Frozen Rows in DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格中冻结行</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>你能够冻结一些行.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="数据表格中冻结" style="width:700px;height:250px"
			data-options="
				singleSelect: true,
				collapsible: true,
				rownumbers: true,
				url: '../datagrid/datagrid_data1.json',
				onLoadSuccess: function(){
					$(this).datagrid('freezeRow',0).datagrid('freezeRow',1);
				}
			">
		<thead data-options="frozen:true">
			<tr>
				<th data-options="field:'itemid',width:100">编号</th>
				<th data-options="field:'productid',width:120">产品编号</th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th data-options="field:'listprice',width:90,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:90,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:230">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

![image-20231015211352105](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152113436.png)

### 数据表格 - 可编辑

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Row Editing DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格中的行编辑</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击行就可以开始编辑.</div>
	</div>
	<div style="margin:10px 0;"></div>

	<table id="dg" class="easyui-datagrid" title="数据表格中的行编辑" style="width:700px;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',
				url: '../datagrid/datagrid_data1.json',
				onClickRow: onClickRow
			">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">产品编号</th>
				<th data-options="field:'productid',width:100,
						formatter:function(value,row){
							return row.productname;
						},
						editor:{
							type:'combobox',
							options:{
								valueField:'productid',
								textField:'productname',
								url:'../datagrid/products.json',
								required:true
							}
						}">产品</th>
				<th data-options="field:'listprice',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right',editor:'numberbox'">成本价</th>
				<th data-options="field:'attr1',width:250,editor:'text'">描述</th>
				<th data-options="field:'status',width:60,align:'center',editor:{type:'checkbox',options:{on:'P',off:''}}">状态</th>
			</tr>
		</thead>
	</table>

	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="remove()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">接受改变</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销改变</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">获取改变</a>
	</div>

	<script type="text/javascript">
		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#dg').datagrid('validateRow', editIndex)){
				var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'productid'});
				var productname = $(ed.target).combobox('getText');
				$('#dg').datagrid('getRows')[editIndex]['productname'] = productname;
				$('#dg').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickRow(index){
			if (editIndex != index){
				if (endEditing()){
					$('#dg').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					editIndex = index;
				} else {
					$('#dg').datagrid('selectRow', editIndex);
				}
			}
		}
		function append(){
			if (endEditing()){
				$('#dg').datagrid('appendRow',{status:'P'});
				editIndex = $('#dg').datagrid('getRows').length-1;
				$('#dg').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		}
		function remove(){
			if (editIndex == undefined){return}
			$('#dg').datagrid('cancelEdit', editIndex)
					.datagrid('deleteRow', editIndex);
			editIndex = undefined;
		}
		function accept(){
			if (endEditing()){
				$('#dg').datagrid('acceptChanges');
			}
		}
		function reject(){
			$('#dg').datagrid('rejectChanges');
			editIndex = undefined;
		}
		function getChanges(){
			var rows = $('#dg').datagrid('getChanges');
			alert(rows.length+' rows are changed!');
		}
	</script>
</body>
</html>
```

![image-20231015211422651](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152114790.png)

### 数据表格 - 带样式

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DataGrid Row Style - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格行样式</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>市场价小于30的行我们高亮显示它.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="数据表格行样式" style="width:700px;height:250px"
			data-options="
				singleSelect: true,
				iconCls: 'icon-save',
				url: '../datagrid/datagrid_data1.json',
				rowStyler: function(index,row){
					if (row.listprice < 30){
						return 'background-color:#6293BB;color:#fff;font-weight:bold;';
					}
				}
			">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

![image-20231015211458383](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152114355.png)

### 数据表格  - 单元格样式

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DataGrid Cell Style - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格单元格样式</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>市场价小于30的单元格我们高亮显示它..</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="数据表格单元格样式" style="width:700px;height:250px"
			data-options="
				singleSelect: true,
				iconCls: 'icon-save',
				url: '../datagrid/datagrid_data1.json'
			">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right',styler:cellStyler">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		function cellStyler(value,row,index){
			if (value < 30){
				return 'background-color:#ffee00;color:red;';
			}
		}
	</script>
</body>
</html>
```

![image-20231015211549106](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152115187.png)

### 数据表格 - 页脚行

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Footer Rows in DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格页脚行</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>统计信息可以在页脚行显示.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="数据表格页脚行" style="width:700px;height:220px"
			data-options="
				url: '../datagrid/datagrid_data2.json',
				fitColumns: true,
				singleSelect: true,
				rownumbers: true,
				showFooter: true
			">
		<thead>
			<tr>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'productid',width:120">产品编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:250">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

![image-20231015211638179](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152116429.png)

### 数据表格 - 合并单元格

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Merge Cells for DataGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数据表格合并单元格</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>数据表格中的单元格可以合并.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-datagrid" title="数据表格合并单元格" style="width:700px;height:250px"
			data-options="
				rownumbers: true,
				singleSelect: true,
				iconCls: 'icon-save',
				url: '../datagrid/datagrid_data1.json',
				onLoadSuccess: onLoadSuccess
			">
		<thead>
			<tr>
				<th data-options="field:'productid',width:100">产品编号</th>
				<th data-options="field:'itemid',width:80">编号</th>
				<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
				<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
				<th data-options="field:'attr1',width:240">描述</th>
				<th data-options="field:'status',width:60,align:'center'">状态</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		function onLoadSuccess(data){
			var merges = [{
				index: 2,
				rowspan: 2
			},{
				index: 5,
				rowspan: 2
			},{
				index: 7,
				rowspan: 2
			}];
			for(var i=0; i<merges.length; i++){
				$(this).datagrid('mergeCells',{
					index: merges[i].index,
					field: 'productid',
					rowspan: merges[i].rowspan
				});
			}
		}
	</script>
</body>
</html>
```

![image-20231015211754984](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152117151.png)

### 数据表格 - 可折叠

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic PropertyGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本属性表格</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击行来改变每个属性值.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="showGroup()">显示分组</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="hideGroup()">隐藏分组</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="showHeader()">显示头部</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="hideHeader()">隐藏头部</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="getChanges()">获取变化</a>
	</div>
	<table id="pg" class="easyui-propertygrid" style="width:300px"
			data-options="url:'../propertygrid/propertygrid_data1.json',showGroup:true,scrollbarSize:0"></table>

	<script type="text/javascript">
		function showGroup(){
			$('#pg').propertygrid({
				showGroup:true
			});
		}
		function hideGroup(){
			$('#pg').propertygrid({
				showGroup:false
			});
		}
		function showHeader(){
			$('#pg').propertygrid({
				showHeader:true
			});
		}
		function hideHeader(){
			$('#pg').propertygrid({
				showHeader:false
			});
		}
		function getChanges(){
			var s = '';
			var rows = $('#pg').propertygrid('getChanges');
			for(var i=0; i<rows.length; i++){
				s += rows[i].name + ':' + rows[i].value + ',';
			}
			alert(s)
		}
	</script>
</body>
</html>
```

![image-20231015211841487](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152118898.png)

## 属性表格

### 属性表格 - 可编辑

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Customize Columns of PropertyGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>属性表格定制列</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>属性表格的列可以改变.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-propertygrid" style="width:300px" data-options="
				url: '../propertygrid/propertygrid_data1.json',
				showGroup: true,
				scrollbarSize: 0,
				columns: mycolumns
			">
	</table>
	<script>
		var mycolumns = [[
    		{field:'name',title:'名称',width:100,sortable:true},
   		    {field:'value',title:'值',width:100,resizable:false}
        ]];
	</script>
</body>
</html>
```

![image-20231015211923709](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152119014.png)

### 属性表格 - 分组格式化

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Group Format - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>分组格式化</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>用户能够改变分组信息.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table class="easyui-propertygrid" style="width:300px" data-options="
				url: '../propertygrid/propertygrid_data1.json',
				showGroup: true,
				scrollbarSize: 0,
				groupFormatter: groupFormatter
			">
	</table>
	<script>
		function groupFormatter(fvalue, rows){
			return fvalue + ' - <span style="color:red">' + rows.length + ' 行</span>';
		}
	</script>
</body>
</html>
```

![image-20231015212007836](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152120205.png)

## 树形菜单

### 基本树形菜单

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Tree - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本树组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击左边箭头来展开或者收缩节点.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<ul class="easyui-tree">
		<li>
			<span>My Documents</span>
			<ul>
				<li data-options="state:'closed'">
					<span>Photos</span>
					<ul>
						<li>
							<span>Friend</span>
						</li>
						<li>
							<span>Wife</span>
						</li>
						<li>
							<span>Company</span>
						</li>
					</ul>
				</li>
				<li>
					<span>Program Files</span>
					<ul>
						<li>Intel</li>
						<li>Java</li>
						<li>Microsoft Office</li>
						<li>Games</li>
					</ul>
				</li>
				<li>index.html</li>
				<li>about.html</li>
				<li>welcome.html</li>
			</ul>
		</li>
	</ul>

</body>
</html>
```

![image-20231015212050406](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152120630.png)

### 树形菜单 - 动画效果

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Animation Tree - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>动画效果树</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>把‘animate’属性设置为true让树有动画效果.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<ul class="easyui-tree" data-options="url:'../tree/tree_data1.json',animate:true"></ul>
</body>
</html>
```

![image-20231015212139455](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152121664.png)

### 树形菜单 - 多选框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CheckBox Tree - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>复选框树</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>带复选框的树节点.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="getChecked()">获取选中值</a>
		<br/>
		<input type="checkbox" checked onchange="$('#tt').tree({cascadeCheck:$(this).is(':checked')})">级联选中
		<input type="checkbox" onchange="$('#tt').tree({onlyLeafCheck:$(this).is(':checked')})">只有叶子节点有复选框
	</div>
	<ul id="tt" class="easyui-tree" data-options="url:'../tree/tree_data1.json',animate:true,checkbox:true"></ul>
	<script type="text/javascript">
		function getChecked(){
			var nodes = $('#tt').tree('getChecked');
			var s = '';
			for(var i=0; i<nodes.length; i++){
				if (s != '') s += ',';
				s += nodes[i].text;
			}
			alert(s);
		}
	</script>
</body>
</html>
```

![image-20231015212211991](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152122180.png)

### 树形菜单 - 树形线

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tree Lines - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>树结构线</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教我们如何显示树结构线.</div>
	</div>
	<div style="margin:10px 0;">
	</div>
	<ul class="easyui-tree" data-options="url:'../tree/tree_data1.json',animate:true,lines:true"></ul>
</body>
</html>
```

![image-20231015212312758](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152123059.png)

### 树形菜单 - 树节点图标

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tree Node Icons - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>树节点图标</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教我们如何在树节点上加图标.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<ul class="easyui-tree" data-options="url:'../tree/tree_data2.json',animate:true"></ul>

</body>
</html>
```

![image-20231015212818892](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152128229.png)

### 树形菜单 - 带操作

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tree Actions - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>树相关操作</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮执行相关操作.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="collapseAll()">收缩所有</a>
		<a href="#" class="easyui-linkbutton" onclick="expandAll()">展开所有</a>
		<a href="#" class="easyui-linkbutton" onclick="expandTo()">展开到</a>
		<a href="#" class="easyui-linkbutton" onclick="getSelected()">获取选中节点</a>
	</div>
	<ul id="tt" class="easyui-tree" data-options="url:'../tree/tree_data1.json',animate:true"></ul>
	<script type="text/javascript">
		function collapseAll(){
			$('#tt').tree('collapseAll');
		}
		function expandAll(){
			$('#tt').tree('expandAll');
		}
		function expandTo(){
			var node = $('#tt').tree('find',113);
			$('#tt').tree('expandTo', node.target).tree('select', node.target);
		}
		function getSelected(){
			var node = $('#tt').tree('getSelected');
			if (node){
				var s = node.text;
				if (node.attributes){
					s += ","+node.attributes.p1+","+node.attributes.p2;
				}
				alert(s);
			}
		}
	</script>
</body>
</html>
```

![image-20231015212905069](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152129408.png)

## 树形表格

### 基本数据表格

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic TreeGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本树形表格</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>树形表格允许你展开和收缩多行组合.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table title="文件浏览" class="easyui-treegrid" style="width:700px;height:250px"
			data-options="
				url: '../treegrid/treegrid_data1.json',
				rownumbers: true,
				idField: 'id',
				treeField: 'name'
			">
		<thead>
			<tr>
				<th data-options="field:'name'" width="220">名称</th>
				<th data-options="field:'size'" width="100" align="right">大小</th>
				<th data-options="field:'date'" width="150">修改日期</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

![image-20231015213016199](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152130651.png)

### 树形表格 - 带操作

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>TreeGrid Actions - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>树形表格相关操作</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮执行相关操作.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="collapseAll()">收缩所有</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="expandAll()">展开所有</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="expandTo()">展开到</a>
	</div>
	<table id="tg" class="easyui-treegrid" title="树形表格相关操作" style="width:700px;height:250px"
			data-options="
				iconCls: 'icon-ok',
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				url: '../treegrid/treegrid_data2.json',
				idField: 'id',
				treeField: 'name'
			">
		<thead>
			<tr>
				<th data-options="field:'name',width:180">任务名称</th>
				<th data-options="field:'persons',width:60,align:'right'">人员</th>
				<th data-options="field:'begin',width:80">开始日期</th>
				<th data-options="field:'end',width:80">结束日期</th>
				<th data-options="field:'progress',width:120,formatter:formatProgress">进度</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		function formatProgress(value){
	    	if (value){
		    	var s = '<div style="width:100%;border:1px solid #ccc">' +
		    			'<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
		    			'</div>';
		    	return s;
	    	} else {
		    	return '';
	    	}
		}
		function collapseAll(){
			$('#tg').treegrid('collapseAll');
		}
		function expandAll(){
			$('#tg').treegrid('expandAll');
		}
		function expandTo(){
			$('#tg').treegrid('expandTo',21).treegrid('select',21);
		}
	</script>
</body>
</html>
```

### 树形表格 - 右键菜单

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>TreeGrid ContextMenu - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>树形表格右键菜单</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>右击树节点显示菜单.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table id="tg" class="easyui-treegrid" title="树形表格右键菜单" style="width:700px;height:250px"
			data-options="
				iconCls: 'icon-ok',
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				url: '../treegrid/treegrid_data2.json',
				idField: 'id',
				treeField: 'name',
				onContextMenu: onContextMenu
			">
		<thead>
			<tr>
				<th data-options="field:'name',width:180">任务名称</th>
				<th data-options="field:'persons',width:60,align:'right'">人员</th>
				<th data-options="field:'begin',width:80">开始日期</th>
				<th data-options="field:'end',width:80">结束日期</th>
				<th data-options="field:'progress',width:120,formatter:formatProgress">进度</th>
			</tr>
		</thead>
	</table>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="removeIt()" data-options="iconCls:'icon-remove'">删除</div>
		<div class="menu-sep"></div>
		<div onclick="collapse()">收缩</div>
		<div onclick="expand()">展开</div>
	</div>
	<script type="text/javascript">
		function formatProgress(value){
	    	if (value){
		    	var s = '<div style="width:100%;border:1px solid #ccc">' +
		    			'<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
		    			'</div>';
		    	return s;
	    	} else {
		    	return '';
	    	}
		}
		function onContextMenu(e,row){
			e.preventDefault();
			$(this).treegrid('select', row.id);
			$('#mm').menu('show',{
				left: e.pageX,
				top: e.pageY
			});
		}
		var idIndex = 100;
		function append(){
			idIndex++;
			var d1 = new Date();
			var d2 = new Date();
			d2.setMonth(d2.getMonth()+1);
			var node = $('#tg').treegrid('getSelected');
			$('#tg').treegrid('append',{
				parent: node.id,
				data: [{
					id: idIndex,
					name: 'New Task'+idIndex,
					persons: parseInt(Math.random()*10),
					begin: $.fn.datebox.defaults.formatter(d1),
					end: $.fn.datebox.defaults.formatter(d2),
					progress: parseInt(Math.random()*100)
				}]
			})
		}
		function removeIt(){
			var node = $('#tg').treegrid('getSelected');
			if (node){
				$('#tg').treegrid('remove', node.id);
			}
		}
		function collapse(){
			var node = $('#tg').treegrid('getSelected');
			if (node){
				$('#tg').treegrid('collapse', node.id);
			}
		}
		function expand(){
			var node = $('#tg').treegrid('getSelected');
			if (node){
				$('#tg').treegrid('expand', node.id);
			}
		}
	</script>
</body>
</html>
```

![image-20231015213204606](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152132811.png)

### 树形表格 - 可编辑

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Editable TreeGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>可编辑树形表格</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>选择一个节点点击编辑按钮执行编辑操作.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="edit()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancel()">结束</a>
	</div>
	<table id="tg" class="easyui-treegrid" title="可编辑树形表格" style="width:700px;height:250px"
			data-options="
				iconCls: 'icon-ok',
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				url: '../treegrid/treegrid_data2.json',
				idField: 'id',
				treeField: 'name',
				showFooter: true
			">
		<thead>
			<tr>
				<th data-options="field:'name',width:180,editor:'text'">任务名称</th>
				<th data-options="field:'persons',width:60,align:'right',editor:'numberbox'">人员</th>
				<th data-options="field:'begin',width:80,editor:'datebox'">开始日期</th>
				<th data-options="field:'end',width:80,editor:'datebox'">结束日期</th>
				<th data-options="field:'progress',width:120,formatter:formatProgress,editor:'numberbox'">进度</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		function formatProgress(value){
	    	if (value){
		    	var s = '<div style="width:100%;border:1px solid #ccc">' +
		    			'<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
		    			'</div>';
		    	return s;
	    	} else {
		    	return '';
	    	}
		}
		var editingId;
		function edit(){
			if (editingId != undefined){
				$('#tg').treegrid('select', editingId);
				return;
			}
			var row = $('#tg').treegrid('getSelected');
			if (row){
				editingId = row.id
				$('#tg').treegrid('beginEdit', editingId);
			}
		}
		function save(){
			if (editingId != undefined){
				var t = $('#tg');
				t.treegrid('endEdit', editingId);
				editingId = undefined;
				var persons = 0;
				var rows = t.treegrid('getChildren');
				for(var i=0; i<rows.length; i++){
					var p = parseInt(rows[i].persons);
					if (!isNaN(p)){
						persons += p;
					}
				}
				var frow = t.treegrid('getFooterRows')[0];
				frow.persons = persons;
				t.treegrid('reloadFooter');
			}
		}
		function cancel(){
			if (editingId != undefined){
				$('#tg').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
		}
	</script>

</body>
</html>
```

![image-20231015213250160](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152132505.png)

### 树形表格 - 固定底部

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>TreeGrid with Footer - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>树形表格底部行</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>在树形表格底部显示总结信息.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table id="tg"></table>
	<script type="text/javascript">
		$(function(){
			$('#tg').treegrid({
				title:'树形表格底部行',
				iconCls:'icon-ok',
				width:700,
				height:250,
				rownumbers: true,
				animate:true,
				collapsible:true,
				fitColumns:true,
				url:'../treegrid/treegrid_data2.json',
				idField:'id',
				treeField:'name',
				showFooter:true,
				columns:[[
	                {title:'任务名称',field:'name',width:180},
					{title:'人员',field:'persons',width:60,align:'right'},
					{title:'开始日期',field:'begin',width:80},
					{title:'结束日期',field:'end',width:80},
					{title:'进度',field:'progress',width:120,
					    formatter:function(value){
					    	if (value){
						    	var s = '<div style="width:100%;border:1px solid #ccc">' +
						    			'<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
						    			'</div>';
						    	return s;
					    	} else {
						    	return '';
					    	}
				    	}
					}
				]]
			});
		})
	</script>
</body>
</html>
```

![image-20231015213323770](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152133934.png)

### 树形表格 - 报表样式

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Reports using TreeGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>树形表格报表</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>使用树形表格显示复杂报表.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table title="树形表格报表" class="easyui-treegrid" style="width:700px;height:250px"
			data-options="
				url: '../treegrid/treegrid_data3.json',
				rownumbers: true,
				showFooter: true,
				idField: 'id',
				treeField: 'region'
			">
		<thead frozen="true">
			<tr>
				<th field="region" width="200">区域</th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th colspan="4">2009</th>
				<th colspan="4">2010</th>
			</tr>
			<tr>
				<th field="f1" width="60" align="right">第一季度</th>
				<th field="f2" width="60" align="right">第二季度</th>
				<th field="f3" width="60" align="right">第三季度</th>
				<th field="f4" width="60" align="right">第四季度</th>
				<th field="f5" width="60" align="right">第一季度</th>
				<th field="f6" width="60" align="right">第二季度</th>
				<th field="f7" width="60" align="right">第三季度</th>
				<th field="f8" width="60" align="right">第四季度</th>
			</tr>
		</thead>
	</table>

</body>
</html>
```

![image-20231015213359641](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152134838.png)

### 树形表格 - 带分页

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Client Side Pagination in TreeGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>树形表格客户端分页</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教我们如何在树形表格中实现客户端分页.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table id="tg" class="easyui-treegrid" title="客户端分页" style="width:700px;height:250px"
			data-options="
				iconCls: 'icon-ok',
				rownumbers: true,
				animate: true,
				collapsible: true,
				fitColumns: true,
				url: '../treegrid/treegrid_data2.json',
				idField: 'id',
				treeField: 'name',
				loadFilter: pagerFilter,
				pagination: true,
				pageSize: 2,
				pageList: [2,5,10]
			">
		<thead>
			<tr>
				<th data-options="field:'name',width:180">任务名称</th>
				<th data-options="field:'persons',width:60,align:'right'">人员</th>
				<th data-options="field:'begin',width:80">开始日期</th>
				<th data-options="field:'end',width:80">结束日期</th>
				<th data-options="field:'progress',width:120,formatter:formatProgress">进度</th>
			</tr>
		</thead>
	</table>
	<script type="text/javascript">
		function formatProgress(value){
	    	if (value){
		    	var s = '<div style="width:100%;border:1px solid #ccc">' +
		    			'<div style="width:' + value + '%;background:#cc0000;color:#fff">' + value + '%' + '</div>'
		    			'</div>';
		    	return s;
	    	} else {
		    	return '';
	    	}
		}

		function pagerFilter(data){
            if ($.isArray(data)){    // is array
                data = {
                    total: data.length,
                    rows: data
                }
            }
            var dg = $(this);
			var state = dg.data('treegrid');
            var opts = dg.treegrid('options');
            var pager = dg.treegrid('getPager');
            pager.pagination({
                onSelectPage:function(pageNum, pageSize){
                    opts.pageNumber = pageNum;
                    opts.pageSize = pageSize;
                    pager.pagination('refresh',{
                        pageNumber:pageNum,
                        pageSize:pageSize
                    });
                    dg.treegrid('loadData',data);
                }
            });
            if (!data.topRows){
            	data.topRows = [];
            	data.childRows = [];
            	for(var i=0; i<data.rows.length; i++){
            		var row = data.rows[i];
            		row._parentId ? data.childRows.push(row) : data.topRows.push(row);
            	}
				data.total = (data.topRows.length);
            }
            var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
            var end = start + parseInt(opts.pageSize);
			data.rows = $.extend(true,[],data.topRows.slice(start, end).concat(data.childRows));
			return data;
		}
	</script>
</body>
</html>
```

![image-20231015213437515](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152134753.png)

## 面板组件

### 基本面板组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Panel - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>基本面板组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>面板组件是其他组件或元素的容器.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="javascript:$('#p').panel('open')">打开</a>
		<a href="#" class="easyui-linkbutton" onclick="javascript:$('#p').panel('close')">关闭</a>
	</div>
	<div id="p" class="easyui-panel" title="基本面板组件" style="width:500px;height:200px;padding:10px;">
		<p style="font-size:14px">jQuery EasyUI框架能够让你轻松构建Web页面.</p>
		<ul>
			<li>easyui是一套基于JQuery的用户界面插件集合.</li>
			<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
			<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
			<li>完美支持HTML5.</li>
			<li>easyui能够有效地节省你的开发时间.</li>
			<li>easyui很简单但是很强大.</li>
		</ul>
	</div>
</body>
</html>
```

![image-20231015213542089](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152135449.png)

### 面板工具

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Panel Tools - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>面板工具</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右上角按钮执行面板相关操作.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#p').panel('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#p').panel('close')">关闭</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#p').panel('expand',true)">展开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#p').panel('collapse',true)">折叠</a>
	</div>
	<div style="height:350px;border:1px solid #ccc;">
		<div id="p" class="easyui-panel" title="面板工具" style="width:500px;height:200px;padding:10px;"
				data-options="iconCls:'icon-save',collapsible:true,minimizable:true,maximizable:true,closable:true">
			<p style="font-size:14px">jQuery EasyUI框架能够让你轻松构建Web页面.</p>
			<ul>
				<li>easyui是一套基于JQuery的用户界面插件集合.</li>
				<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
				<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
				<li>完美支持HTML5.</li>
				<li>easyui能够有效地节省你的开发时间.</li>
				<li>easyui很简单但是很强大.</li>
			</ul>
		</div>
	</div>

</body>
</html>
```

![image-20231015213615621](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152136973.png)

### 定制工具

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom Panel Tools - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>定制面板工具</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右上角按钮执行面板相关操作.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-panel" title="定制面板工具" style="width:500px;height:200px;padding:10px;"
			data-options="iconCls:'icon-save',closable:true,tools:'#tt'">
		<p style="font-size:14px">jQuery EasyUI框架能够让你轻松构建Web页面.</p>
		<ul>
			<li>easyui是一套基于JQuery的用户界面插件集合.</li>
			<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
			<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
			<li>完美支持HTML5.</li>
			<li>easyui能够有效地节省你的开发时间.</li>
			<li>easyui很简单但是很强大.</li>
		</ul>
	</div>
	<div id="tt">
		<a href="javascript:void(0)" class="icon-add" onclick="javascript:alert('添加')"></a>
		<a href="javascript:void(0)" class="icon-edit" onclick="javascript:alert('编辑')"></a>
		<a href="javascript:void(0)" class="icon-cut" onclick="javascript:alert('剪切')"></a>
		<a href="javascript:void(0)" class="icon-help" onclick="javascript:alert('帮助')"></a>
	</div>
</body>
</html>
```

![image-20231015213644387](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152136803.png)

### 加载内容

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Load Panel Content - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>加载面板内容</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击面板右上角按钮加载内容.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div id="p" class="easyui-panel" title="加载面板内容" style="width:500px;height:200px;padding:10px;"
			data-options="
				tools:[{
					iconCls:'icon-reload',
					handler:function(){
						$('#p').panel('refresh', '../panel/_content.html');
					}
				}]
			">
	</div>
</body>
</html>
```

![image-20231015213717450](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152137803.png)

### 嵌套面板

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Nested Panel - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<h2>嵌套面板</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>面板能够放在容器里，并且能包含其他组件.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-panel" title="嵌套面板" style="width:500px;height:200px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'west',split:true" style="width:100px;padding:10px">
				左边内容
			</div>
			<div data-options="region:'center'" style="padding:10px">
				右边内容
			</div>
		</div>
	</div>
</body>
</html>
```

![image-20231015213750455](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152137880.png)

### 可折叠的面板组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Accordion - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本可折叠面板</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击面板头部来显示它的内容.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-accordion" style="width:500px;height:300px;">
		<div title="关于" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
			<h3 style="color:#0099FF;">可折叠面板</h3>
			<p>可折叠面板是jquery easyui框架的一部分. 他可以让你轻松地在Web页面上定义可折叠元素.</p>
		</div>
		<div title="帮助" data-options="iconCls:'icon-help'" style="padding:10px;">
			<p>可折叠面板允许你同时显示多个面板，每个面板支持展开和折叠，点击面板头部展开和折叠面板体，面板内容可以通过AJAX加载，用户能够定义某一个面板默认选中，如果没有指定，则第一个面板默认选中。</p>
		</div>
		<div title="树菜单" data-options="iconCls:'icon-search'" style="padding:10px;">
			<ul class="easyui-tree">
				<li>
					<span>食物</span>
					<ul>
						<li>
							<span>水果</span>
							<ul>
								<li>苹果</li>
								<li>橙子</li>
							</ul>
						</li>
						<li>
							<span>蔬菜</span>
							<ul>
								<li>西红柿</li>
								<li>胡萝卜</li>
								<li>卷心菜</li>
								<li>土豆</li>
								<li>生菜</li>
							</ul>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>
```

![image-20231015214359309](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152144744.png)

### 可折叠的面板工具

datagrid_data1.json

```json
{"total":28,"rows":[
	{"productid":"FI-SW-01","productname":"Koi","unitcost":10.00,"status":"P","listprice":36.50,"attr1":"Large","itemid":"EST-1"},
	{"productid":"K9-DL-01","productname":"Dalmation","unitcost":12.00,"status":"P","listprice":18.50,"attr1":"Spotted Adult Female","itemid":"EST-10"},
	{"productid":"RP-SN-01","productname":"Rattlesnake","unitcost":12.00,"status":"P","listprice":38.50,"attr1":"Venomless","itemid":"EST-11"},
	{"productid":"RP-SN-01","productname":"Rattlesnake","unitcost":12.00,"status":"P","listprice":26.50,"attr1":"Rattleless","itemid":"EST-12"},
	{"productid":"RP-LI-02","productname":"Iguana","unitcost":12.00,"status":"P","listprice":35.50,"attr1":"Green Adult","itemid":"EST-13"},
	{"productid":"FL-DSH-01","productname":"Manx","unitcost":12.00,"status":"P","listprice":158.50,"attr1":"Tailless","itemid":"EST-14"},
	{"productid":"FL-DSH-01","productname":"Manx","unitcost":12.00,"status":"P","listprice":83.50,"attr1":"With tail","itemid":"EST-15"},
	{"productid":"FL-DLH-02","productname":"Persian","unitcost":12.00,"status":"P","listprice":23.50,"attr1":"Adult Female","itemid":"EST-16"},
	{"productid":"FL-DLH-02","productname":"Persian","unitcost":12.00,"status":"P","listprice":89.50,"attr1":"Adult Male","itemid":"EST-17"},
	{"productid":"AV-CB-01","productname":"Amazon Parrot","unitcost":92.00,"status":"P","listprice":63.50,"attr1":"Adult Male","itemid":"EST-18"}
]}
```

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Accordion Tools - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>可折叠面板工具</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击面板右上角工具执行相关操作.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-accordion" style="width:500px;height:300px;">
		<div title="关于" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
			<h3 style="color:#0099FF;">可折叠面板</h3>
			<p>可折叠面板是jquery easyui框架的一部分. 他可以让你轻松地在Web页面上定义可折叠元素.</p>
		</div>
		<div title="帮助" data-options="iconCls:'icon-help'" style="padding:10px;">
			<p>可折叠面板允许你同时显示多个面板，每个面板支持展开和折叠，点击面板头部展开和折叠面板体，面板内容可以通过AJAX加载，用户能够定义某一个面板默认选中，如果没有指定，则第一个面板默认选中。</p>
		</div>
		<div title="数据表格" style="padding:10px" data-options="
				selected:true,
				tools:[{
					iconCls:'icon-reload',
					handler:function(){
						$('#dg').datagrid('reload');
					}
				}]">
			<table id="dg" class="easyui-datagrid"
					data-options="url:'../accordion/datagrid_data1.json',fit:true,fitColumns:true,singleSelect:true">
				<thead>
					<tr>
						<th data-options="field:'itemid',width:80">编号</th>
						<th data-options="field:'productid',width:100">商品编号</th>
						<th data-options="field:'listprice',width:80,align:'right'">市场价</th>
						<th data-options="field:'unitcost',width:80,align:'right'">成本价</th>
						<th data-options="field:'attr1',width:150">描述</th>
						<th data-options="field:'status',width:50,align:'center'">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>
```

![image-20231015214509872](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152145689.png)

### 异步加载的面板

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>AJAX Content</title>
</head>
<body>
	<p style="font-size:14px">这里是AJAX加载内容.</p>
	<ul>
		<li>easyui是一套基于JQuery的用户界面插件集合.</li>
		<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
		<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
		<li>完美支持HTML5.</li>
		<li>easyui能够有效地节省你的开发时间.</li>
		<li>easyui很简单但是很强大.</li>
	</ul>
</body>
</html>
```

![image-20231015214557206](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152145632.png)

### 可折叠面板相关操作

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Accordion Actions - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>可折叠面板相关操作</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮添加或者删除可折叠面板项.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="selectPanel()">选择</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addPanel()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="removePanel()">删除</a>
	</div>
	<div id="aa" class="easyui-accordion" style="width:500px;height:300px;">
		<div title="关于" data-options="iconCls:'icon-ok'" style="overflow:auto;padding:10px;">
			<h3 style="color:#0099FF;">可折叠面板</h3>
			<p>可折叠面板是jquery easyui框架的一部分. 他可以让你轻松地在Web页面上定义可折叠元素.</p>
		</div>
	</div>
	<script type="text/javascript">
		function selectPanel(){
			$.messager.prompt('系统提示','请输入面板标题:',function(s){
				if (s){
					$('#aa').accordion('select',s);
				}
			});
		}
		var idx = 1;
		function addPanel(){
			$('#aa').accordion('add',{
				title:'标题'+idx,
				content:'<div style="padding:10px">内容'+idx+'</div>'
			});
			idx++;
		}
		function removePanel(){
			var pp = $('#aa').accordion('getSelected');
			if (pp){
				var index = $('#aa').accordion('getPanelIndex',pp);
				$('#aa').accordion('remove',index);
			}
		}
	</script>
</body>
</html>
```

![image-20231015214650526](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152146020.png)

## 选项卡组件

### 基本选项卡

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Tabs - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本选项卡组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击选项卡切换面板内容.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-tabs" style="width:700px;height:250px">
		<div title="关于" style="padding:10px">
			<p style="font-size:14px">jQuery EasyUI框架能够让你轻松构建Web页面.</p>
			<ul>
				<li>easyui是一套基于JQuery的用户界面插件集合.</li>
				<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
				<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
				<li>完美支持HTML5.</li>
				<li>easyui能够有效地节省你的开发时间.</li>
				<li>easyui很简单但是很强大.</li>
			</ul>
		</div>
		<div title="我的文档" style="padding:10px">
			<ul class="easyui-tree" data-options="url:'../tabs/tree_data1.json',animate:true"></ul>
		</div>
		<div title="帮助" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
			这是帮助内容.
		</div>
	</div>
</body>
</html>
```

![image-20231015213913720](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152139942.png)

### 自适应的选项卡

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Auto Height for Tabs - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>选项卡自适应高度</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>选项卡根据选项面板内容自适应高度.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-tabs" style="width:700px;height:auto">
		<div title="关于" style="padding:10px">
			<p style="font-size:14px">jQuery EasyUI框架能够让你轻松构建Web页面.</p>
			<ul>
				<li>easyui是一套基于JQuery的用户界面插件集合.</li>
				<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
				<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
				<li>完美支持HTML5.</li>
				<li>easyui能够有效地节省你的开发时间.</li>
				<li>easyui很简单但是很强大.</li>
			</ul>
		</div>
		<div title="我的文档" style="padding:10px">
			<ul class="easyui-tree" data-options="url:'../tabs/tree_data1.json',animate:true"></ul>
		</div>
		<div title="帮助" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
			这是帮助内容.
		</div>
	</div>
</body>
</html>
```

![image-20231015213943614](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152139921.png)

### 自动切换

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Hover Tabs - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>选项卡自动切换</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>鼠标移动到选项卡上自动切换选项卡内容.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div id="tt" class="easyui-tabs" style="width:700px;height:250px">
		<div title="关于" style="padding:10px">
			<p style="font-size:14px">jQuery EasyUI框架能够让你轻松构建Web页面.</p>
			<ul>
				<li>easyui是一套基于JQuery的用户界面插件集合.</li>
				<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
				<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
				<li>完美支持HTML5.</li>
				<li>easyui能够有效地节省你的开发时间.</li>
				<li>easyui很简单但是很强大.</li>
			</ul>
		</div>
		<div title="我的文档" style="padding:10px">
			<ul class="easyui-tree" data-options="url:'../tabs/tree_data1.json',animate:true"></ul>
		</div>
		<div title="帮助" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
			这是帮助内容.
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			var tabs = $('#tt').tabs().tabs('tabs');
			for(var i=0; i<tabs.length; i++){
				tabs[i].panel('options').tab.unbind().bind('mouseenter',{index:i},function(e){
					$('#tt').tabs('select', e.data.index);
				});
			}
		});
	</script>
</body>
</html>
```

![image-20231015214021624](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152140901.png)

### 嵌套选项卡

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Nested Tabs - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>选项卡嵌套</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>选项卡面板能够包含子选项卡或者其他元素.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-tabs" data-options="tools:'#tab-tools'" style="width:700px;height:250px">
		<div title="子选项卡" style="padding:10px;">
			<div class="easyui-tabs" data-options="fit:true,plain:true">
				<div title="标题一" style="padding:10px;">内容一</div>
				<div title="标题二" style="padding:10px;">内容二</div>
				<div title="标题三" style="padding:10px;">内容三</div>
			</div>
		</div>
		<div title="Ajax" data-options="href:'../tabs/_content.html',closable:true" style="padding:10px"></div>
		<div title="嵌套框架" data-options="closable:true" style="overflow:hidden">
			<iframe scrolling="yes" frameborder="0"  src="http://www.jeasyui.com" style="width:100%;height:100%;"></iframe>
		</div>
		<div title="数据表格" data-options="closable:true" style="padding:10px">
			<table class="easyui-datagrid" data-options="fit:true,singleSelect:true,rownumbers:true">
				<thead>
					<tr>
						<th data-options="field:'f1',width:100">标题一</th>
						<th data-options="field:'f2',width:100">标题二</th>
						<th data-options="field:'f3',width:100">标题三</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>d11</td>
						<td>d12</td>
						<td>d13</td>
					</tr>
					<tr>
						<td>d21</td>
						<td>d22</td>
						<td>d23</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

</body>
</html>
```

![image-20231015214105512](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152141100.png)

### 带有工具的选项卡

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tabs Strip Tools - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>选项卡片工具</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击选项卡片上的小按钮执行相关操作.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-tabs" style="width:700px;height:250px">
		<div title="关于" data-options="tools:'#p-tools'" style="padding:10px">
			<p style="font-size:14px">jQuery EasyUI框架能够让你轻松构建Web页面.</p>
			<ul>
				<li>easyui是一套基于JQuery的用户界面插件集合.</li>
				<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
				<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
				<li>完美支持HTML5.</li>
				<li>easyui能够有效地节省你的开发时间.</li>
				<li>easyui很简单但是很强大.</li>
			</ul>
		</div>
		<div title="帮助" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
			这是帮助内容.
		</div>
	</div>
	<div id="p-tools">
		<a href="javascript:void(0)" class="icon-mini-add" onclick="alert('添加')"></a>
		<a href="javascript:void(0)" class="icon-mini-edit" onclick="alert('编辑')"></a>
		<a href="javascript:void(0)" class="icon-mini-refresh" onclick="alert('刷新')"></a>
	</div>

</body>
</html>
```

![image-20231015214140023](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152141370.png)

### 选项卡工具

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tabs Tools - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>选项卡工具</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击选项卡右边按钮添加或者删除选项卡面板.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools'" style="width:700px;height:250px">
	</div>
	<div id="tab-tools">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addPanel()"></a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="removePanel()"></a>
	</div>
	<script type="text/javascript">
		var index = 0;
		function addPanel(){
			index++;
			$('#tt').tabs('add',{
				title: '选项卡'+index,
				content: '<div style="padding:10px">内容'+index+'</div>',
				closable: true
			});
		}
		function removePanel(){
			var tab = $('#tt').tabs('getSelected');
			if (tab){
				var index = $('#tt').tabs('getTabIndex', tab);
				$('#tt').tabs('close', index);
			}
		}
	</script>
</body>
</html>
```

![image-20231015214301007](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152143741.png)

### 选项卡位置

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Tab Position - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>选项卡位置</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击“位置”下拉框选择一个项改变选项卡的位置.</div>
	</div>
	<div style="margin:10px 0;">
		<span>位置:</span>
		<select onchange="$('#tt').tabs({tabPosition:this.value})">
			<option value="top">上</option>
			<option value="bottom">下</option>
			<option value="left">左</option>
			<option value="right">右</option>
		</select>
	</div>
	<div id="tt" class="easyui-tabs" style="width:700px;height:250px">
		<div title="关于" style="padding:10px">
			<p style="font-size:14px">jQuery EasyUI框架能够让你轻松构建Web页面.</p>
			<ul>
				<li>easyui是一套基于JQuery的用户界面插件集合.</li>
				<li>easyui为构建现代流行的交互式体验JavaScript应用程序提供了基本功能.</li>
				<li>使用easyui你不需要写很多javascript代码，你通常只需要写一些html标签来定义用户界面.</li>
				<li>完美支持HTML5.</li>
				<li>easyui能够有效地节省你的开发时间.</li>
				<li>easyui很简单但是很强大.</li>
			</ul>
		</div>
		<div title="我的文档" style="padding:10px">
			<ul class="easyui-tree" data-options="url:'../tabs/tree_data1.json',animate:true"></ul>
		</div>
		<div title="帮助" data-options="iconCls:'icon-help',closable:true" style="padding:10px">
			这是帮助内容.
		</div>
	</div>

</body>
</html>
```

![image-20231015214248067](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152142628.png)

## 布局组件

### 基本布局组件

datagrid_data1.json

```json
{"total":28,"rows":[
	{"productid":"FI-SW-01","productname":"Koi","unitcost":10.00,"status":"P","listprice":36.50,"attr1":"Large","itemid":"EST-1"},
	{"productid":"K9-DL-01","productname":"Dalmation","unitcost":12.00,"status":"P","listprice":18.50,"attr1":"Spotted Adult Female","itemid":"EST-10"},
	{"productid":"RP-SN-01","productname":"Rattlesnake","unitcost":12.00,"status":"P","listprice":38.50,"attr1":"Venomless","itemid":"EST-11"},
	{"productid":"RP-SN-01","productname":"Rattlesnake","unitcost":12.00,"status":"P","listprice":26.50,"attr1":"Rattleless","itemid":"EST-12"},
	{"productid":"RP-LI-02","productname":"Iguana","unitcost":12.00,"status":"P","listprice":35.50,"attr1":"Green Adult","itemid":"EST-13"},
	{"productid":"FL-DSH-01","productname":"Manx","unitcost":12.00,"status":"P","listprice":158.50,"attr1":"Tailless","itemid":"EST-14"},
	{"productid":"FL-DSH-01","productname":"Manx","unitcost":12.00,"status":"P","listprice":83.50,"attr1":"With tail","itemid":"EST-15"},
	{"productid":"FL-DLH-02","productname":"Persian","unitcost":12.00,"status":"P","listprice":23.50,"attr1":"Adult Female","itemid":"EST-16"},
	{"productid":"FL-DLH-02","productname":"Persian","unitcost":12.00,"status":"P","listprice":89.50,"attr1":"Adult Male","itemid":"EST-17"},
	{"productid":"AV-CB-01","productname":"Amazon Parrot","unitcost":92.00,"status":"P","listprice":63.50,"attr1":"Adult Male","itemid":"EST-18"}
]}
```



```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本布局组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>布局包含北，南，西，东和中五个区域.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-layout" style="width:700px;height:350px;">
		<div data-options="region:'north'" style="height:50px"></div>
		<div data-options="region:'south',split:true" style="height:50px;"></div>
		<div data-options="region:'east',split:true" title="东" style="width:180px;"></div>
		<div data-options="region:'west',split:true" title="西" style="width:100px;"></div>
		<div data-options="region:'center',title:'主区域标题',iconCls:'icon-ok'">
			<table class="easyui-datagrid"
					data-options="url:'../layout/datagrid_data1.json',border:false,singleSelect:true,fit:true,fitColumns:true">
				<thead>
					<tr>
						<th data-options="field:'itemid'" width="80">编号</th>
						<th data-options="field:'productid'" width="100">商品编号</th>
						<th data-options="field:'listprice',align:'right'" width="80">市场价</th>
						<th data-options="field:'unitcost',align:'right'" width="80">成本价</th>
						<th data-options="field:'attr1'" width="150">描述</th>
						<th data-options="field:'status',align:'center'" width="50">状态</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>

</body>
</html>
```

![image-20231015214741492](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152147208.png)

### 全屏布局

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Full Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">北部区域</div>
	<div data-options="region:'west',split:true,title:'西'" style="width:150px;padding:10px;">西部区域内容</div>
	<div data-options="region:'east',split:true,collapsed:true,title:'东'" style="width:100px;padding:10px;">中部区域</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#A9FACD;padding:10px;">南部区域</div>
	<div data-options="region:'center',title:'中'"></div>
</body>
</html>
```

![image-20231015214854129](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152148717.png)

### 嵌套布局

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Nested Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>嵌套布局</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>布局区域面板能够包含其他布局或者其他组件.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-layout" style="width:700px;height:350px;">
		<div data-options="region:'north'" style="height:50px"></div>
		<div data-options="region:'south',split:true" style="height:50px;"></div>
		<div data-options="region:'east',split:true" title="东" style="width:180px;"></div>
		<div data-options="region:'west',split:true" title="西" style="width:100px;"></div>
		<div data-options="region:'center',iconCls:'icon-ok'" title="中">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',split:true,border:false" style="height:50px"></div>
				<div data-options="region:'west',split:true,border:false" style="width:100px"></div>
				<div data-options="region:'center',border:false"></div>
			</div>
		</div>
	</div>

</body>
</html>
```

![image-20231015214926780](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152149370.png)

### 布局中无折叠按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>No collapsible button in Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>布局中无折叠按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>布局区域面板中无折叠按钮.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-layout" style="width:700px;height:350px;">
		<div data-options="region:'north'" style="height:50px"></div>
		<div data-options="region:'south',split:true" style="height:50px;"></div>
		<div data-options="region:'east',split:true,title:'东',collapsible:false" style="width:250px;">
			<table id="tt" class="easyui-propertygrid"
					data-options="
						url: '../layout/propertygrid_data1.json',
						showGroup: true,
						fit: true,
						border: false
					">
			</table>
		</div>
		<div data-options="region:'center',title:'主面板标题',iconCls:'icon-ok',href:'../layout/_content.html'" style="padding:10px">
		</div>
	</div>

</body>
</html>
```

![image-20231015215000056](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152150956.png)

### 添加和删除布局

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add and Remove Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>添加和删除布局组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮添加或者删除布局组件面板.</div>
	</div>
	<div style="margin:10px 0;">
		<span>选择一个区域面板:</span>
		<select id="region">
			<option value="north">北</option>
			<option value="south">南</option>
			<option value="east">东</option>
			<option value="west">西</option>
		</select>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="addPanel()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="removePanel()">删除</a>
	</div>
	<div id="cc" class="easyui-layout" style="width:700px;height:350px;">
		<div data-options="region:'north'" style="height:50px"></div>
		<div data-options="region:'south',split:true" style="height:50px;"></div>
		<div data-options="region:'east',split:true" title="东" style="width:100px;"></div>
		<div data-options="region:'west',split:true" title="西" style="width:100px;"></div>
		<div data-options="region:'center',title:'中'"></div>
	</div>
	<script type="text/javascript">
		function addPanel(){
			var region = $('#region').val();
			var options = {
				region: region
			};
			if (region=='north' || region=='south'){
				options.height = 50;
			} else {
				options.width = 100;
				options.split = true;
				options.title = $('#region option:selected').text();
			}
			$('#cc').layout('add', options);
		}
		function removePanel(){
			$('#cc').layout('remove', $('#region').val());
		}
	</script>
</body>
</html>
```

### ![image-20231015215038729](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152150491.png)复杂布局

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Complex Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>复杂布局</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教大家如何创建一个复杂布局.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-layout" style="width:700px;height:350px;">
		<div data-options="region:'north'" style="height:50px"></div>
		<div data-options="region:'south',split:true" style="height:50px;"></div>
		<div data-options="region:'east',split:true" title="东" style="width:180px;">
			<ul class="easyui-tree" data-options="url:'../layout/tree_data1.json',animate:true,dnd:true"></ul>
		</div>
		<div data-options="region:'west',split:true" title="西" style="width:100px;">
			<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="标题一" style="padding:10px;">
					内容一
				</div>
				<div title="标题二" data-options="selected:true" style="padding:10px;">
					内容二
				</div>
				<div title="标题三" style="padding:10px">
					内容三
				</div>
			</div>
		</div>
		<div data-options="region:'center',title:'主面板区域',iconCls:'icon-ok'">
			<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
				<div title="关于" data-options="href:'../layout/_content.html'" style="padding:10px"></div>
				<div title="数据表格" style="padding:5px">
					<table class="easyui-datagrid"
							data-options="url:'../layout/datagrid_data1.json',singleSelect:true,fit:true,fitColumns:true">
						<thead>
							<tr>
								<th data-options="field:'itemid'" width="80">编号</th>
								<th data-options="field:'productid'" width="100">商品编号</th>
								<th data-options="field:'listprice',align:'right'" width="80">市场价</th>
								<th data-options="field:'unitcost',align:'right'" width="80">成本价</th>
								<th data-options="field:'attr1'" width="150">描述</th>
								<th data-options="field:'status',align:'center'" width="50">状态</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
```

![image-20231015215132190](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152151682.png)

## 窗体组件

### 基本窗体

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Window - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本窗体组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>窗体能够在屏幕中自由拖动.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">关闭</a>
	</div>
	<div id="w" class="easyui-window" title="基本窗体组件" data-options="iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">
		窗体内容。
	</div>
</body>
</html>
```

![image-20231015215259557](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152153557.png)

### 定制窗体工具

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom Window Tools - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>定制窗体工具</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击窗体右上角按钮执行相关操作.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">关闭</a>
	</div>
	<div id="w" class="easyui-window" title="Custom Window Tools" data-options="iconCls:'icon-save',minimizable:false,tools:'#tt'" style="width:500px;height:200px;padding:10px;">
		窗体内容
	</div>
	<div id="tt">
		<a href="javascript:void(0)" class="icon-add" onclick="javascript:alert('add')"></a>
		<a href="javascript:void(0)" class="icon-edit" onclick="javascript:alert('edit')"></a>
		<a href="javascript:void(0)" class="icon-cut" onclick="javascript:alert('cut')"></a>
		<a href="javascript:void(0)" class="icon-help" onclick="javascript:alert('help')"></a>
	</div>

</body>
</html>
```

![image-20231015222056666](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152221370.png)



### 内部窗体

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Inline Window - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>内部窗体</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>内部窗体在它的父层里面.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">关闭</a>
	</div>
	<div style="position:relative;width:500px;height:300px;border:1px solid #ccc;overflow:auto;">
		<div id="w" class="easyui-window" data-options="title:'内部窗体',inline:true" style="width:250px;height:150px;padding:10px">
			窗体在它的父层里面。
		</div>
	</div>

</body>
</html>
```

![image-20231015222131561](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152221568.png)



### 模态框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Modal Window - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>模态窗口</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮打开模态窗口.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">关闭</a>
	</div>
	<div id="w" class="easyui-window" title="模态窗口" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">
		窗体内容
	</div>

</body>
</html>
```



![image-20231015222158250](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152221432.png)



### 窗体布局

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Window Layout - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>窗体布局</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>在窗体中使用布局.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">关闭</a>
	</div>
	<div id="w" class="easyui-window" title="窗体布局" data-options="iconCls:'icon-save'" style="width:500px;height:200px;padding:5px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'east',split:true" style="width:100px"></div>
			<div data-options="region:'center'" style="padding:10px;">
				jQuery EasyUI框架能够让你轻松构建Web页面.
			</div>
			<div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">
				<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:alert('ok')">确定</a>
				<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:alert('cancel')">关闭</a>
			</div>
		</div>
	</div>

</body>
</html>
```

![image-20231015222228065](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152222437.png)





### 对话框窗体

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Dialog - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本对话窗体组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮打开或者关闭对话窗体.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('close')">关闭</a>
	</div>
	<div id="dlg" class="easyui-dialog" title="基本对话框" data-options="iconCls:'icon-save'" style="width:400px;height:200px;padding:10px">
		对话窗体内容。
	</div>
</body>
</html>
```

![image-20231015222302607](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152223642.png)

### 工具栏和按钮

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Toolbar and Buttons - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>工具栏和按钮</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>工具栏和按钮可以添加到对话框体里去.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('close')">关闭</a>
	</div>
	<div id="dlg" class="easyui-dialog" title="工具栏和按钮" style="width:400px;height:200px;padding:10px"
			data-options="
				iconCls: 'icon-save',
				toolbar: [{
					text:'添加',
					iconCls:'icon-add',
					handler:function(){
						alert('添加')
					}
				},'-',{
					text:'保存',
					iconCls:'icon-save',
					handler:function(){
						alert('保存')
					}
				}],
				buttons: [{
					text:'确定',
					iconCls:'icon-ok',
					handler:function(){
						alert('确定');
					}
				},{
					text:'关闭',
					handler:function(){
						alert('关闭');;
					}
				}]
			">
		对话窗体内容.
	</div>

</body>
</html>
```

### 复杂工具栏

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Complex Toolbar on Dialog - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>对话窗体上的复杂工具栏</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>这个示例教你如何在对话窗体上创建复杂工具栏.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('open')">打开</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#dlg').dialog('close')">关闭</a>
	</div>
	<div id="dlg" class="easyui-dialog" title="对话窗体上的复杂工具栏" style="width:400px;height:200px;padding:10px"
			data-options="
				iconCls: 'icon-save',
				toolbar: '#dlg-toolbar',
				buttons: '#dlg-buttons'
			">
		对话窗体内容.
	</div>
	<div id="dlg-toolbar" style="padding:2px 0">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="padding-left:2px">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">编辑</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">帮助</a>
				</td>
				<td style="text-align:right;padding-right:2px">
					<input class="easyui-searchbox" data-options="prompt:'请输入...'" style="width:150px"></input>
				</td>
			</tr>
		</table>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:alert('保存')">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">关闭</a>
	</div>

</body>
</html>
```

![image-20231015222349560](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152223631.png)

## 消息框组件

### 基本消息框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Messager - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本消息组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击每个按钮查看不同的消息框.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="show()">普通消息显示</a>
		<a href="#" class="easyui-linkbutton" onclick="slide()">滑动消息显示</a>
		<a href="#" class="easyui-linkbutton" onclick="fade()">淡出消息显示</a>
		<a href="#" class="easyui-linkbutton" onclick="progress()">进度条显示</a>
	</div>
	<script type="text/javascript">
		function show(){
			$.messager.show({
				title:'我的标题',
				msg:'消息4秒后自动关闭.',
				showType:'show'
			});
		}
		function slide(){
			$.messager.show({
				title:'我的标题',
				msg:'消息5秒后自动关闭.',
				timeout:5000,
				showType:'slide'
			});
		}
		function fade(){
			$.messager.show({
				title:'我的标题',
				msg:'消息不会自动关闭.',
				timeout:0,
				showType:'fade'
			});
		}
		function progress(){
			var win = $.messager.progress({
				title:'请稍等...',
				msg:'加载数据中...'
			});
			setTimeout(function(){
				$.messager.progress('close');
			},5000)
		}
	</script>
</body>
</html>
```

![image-20231015222439239](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152224226.png)

### 提醒消息框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Alert Messager - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>提醒消息组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击每个按钮显示不同的提醒消息框.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="alert1()">提醒消息</a>
		<a href="#" class="easyui-linkbutton" onclick="alert2()">错误消息</a>
		<a href="#" class="easyui-linkbutton" onclick="alert3()">信息消息</a>
		<a href="#" class="easyui-linkbutton" onclick="alert4()">问题消息</a>
		<a href="#" class="easyui-linkbutton" onclick="alert5()">警告消息</a>
	</div>
	<script>
		function alert1(){
			$.messager.alert('我的标题','这是一个消息!');
		}
		function alert2(){
			$.messager.alert('我的标题','这是一个错误消息!','error');
		}
		function alert3(){
			$.messager.alert('我的标题','这是一个信息消息!','info');
		}
		function alert4(){
			$.messager.alert('我的标题','这是一个问题消息!','question');
		}
		function alert5(){
			$.messager.alert('我的标题','这是一个警告消息!','warning');
		}
	</script>
</body>
</html>
```

![image-20231015222514567](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152225420.png)

### 交互式消息弹窗

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Interactive Messager - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>交互式消息组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击每个按钮显示不同的交互式消息框.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="confirm1();">确认消息框</a>
		<a href="#" class="easyui-linkbutton" onclick="prompt1()">提示消息框</a>
	</div>
	<script>
		function confirm1(){
			$.messager.confirm('我的标题', '确认吗?', function(r){
				if (r){
					alert('确认: '+r);
				}
			});
		}
		function prompt1(){
			$.messager.prompt('我的标题', '请输些东西', function(r){
				if (r){
					alert('你输入的是: '+r);
				}
			});
		}
	</script>
</body>
</html>
```

![image-20231015222543718](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152225894.png)

### 消息窗的位置

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Message Box Position - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>消息框位置</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮在不同位置显示消息框.</div>
	</div>
	<div style="margin:10px 0;">
		<p>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="topLeft();">左上角</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="topCenter()">顶部左右居中</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="topRight()">右上角</a>
		</p>
		<p>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="centerLeft()">左边上下居中</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="center()">上下左右居中</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="centerRight()">右边上下居中</a>
		</p>
		<p>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="bottomLeft()">左下角</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="bottomCenter()">底部左右居中</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="bottomRight()">右下角</a>
		</p>
	</div>
	<script>
		function topLeft(){
			$.messager.show({
				title:'我的标题',
				msg:'左上角',
				showType:'show',
				style:{
					right:'',
					left:0,
					top:document.body.scrollTop+document.documentElement.scrollTop,
					bottom:''
				}
			});
		}
		function topCenter(){
			$.messager.show({
				title:'我的标题',
				msg:'顶部左右居中',
				showType:'slide',
				style:{
					right:'',
					top:document.body.scrollTop+document.documentElement.scrollTop,
					bottom:''
				}
			});
		}
		function topRight(){
			$.messager.show({
				title:'我的标题',
				msg:'右上角',
				showType:'show',
				style:{
					left:'',
					right:0,
					top:document.body.scrollTop+document.documentElement.scrollTop,
					bottom:''
				}
			});
		}
		function centerLeft(){
			$.messager.show({
				title:'我的标题',
				msg:'左边上下居中',
				showType:'fade',
				style:{
					left:0,
					right:'',
					bottom:''
				}
			});
		}
		function center(){
			$.messager.show({
				title:'我的标题',
				msg:'上下左右居中',
				showType:'fade',
				style:{
					right:'',
					bottom:''
				}
			});
		}
		function centerRight(){
			$.messager.show({
				title:'我的标题',
				msg:'右边上下居中',
				showType:'fade',
				style:{
					left:'',
					right:0,
					bottom:''
				}
			});
		}
		function bottomLeft(){
			$.messager.show({
				title:'我的标题',
				msg:'左下角',
				showType:'show',
				style:{
					left:0,
					right:'',
					top:'',
					bottom:-document.body.scrollTop-document.documentElement.scrollTop
				}
			});
		}
		function bottomCenter(){
			$.messager.show({
				title:'我的标题',
				msg:'底部左右居中',
				showType:'slide',
				style:{
					right:'',
					top:'',
					bottom:-document.body.scrollTop-document.documentElement.scrollTop
				}
			});
		}
		function bottomRight(){
			$.messager.show({
				title:'我的标题',
				msg:'右下角',
				showType:'show'
			});
		}
	</script>
</body>
</html>
```

![image-20231015222613688](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152226666.png)

## 日期框组件

### 基本日期框组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic DateBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本日期框组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右边日历小图标.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-datebox"></input>
</body>
</html>
```

![image-20231015222723485](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152227553.png)

### 格式化日期框组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Date Format - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>日期格式化</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>用不同的格式格式化不同的日期框组件.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-datebox"></input>
	<input class="easyui-datebox" data-options="formatter:myformatter,parser:myparser"></input>
	<script type="text/javascript">
		function myformatter(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			return y+'/'+(m<10?('0'+m):m)+'/'+(d<10?('0'+d):d);
		}
		function myparser(s){
			if (!s) return new Date();
			var ss = (s.split('/'));
			var y = parseInt(ss[0],10);
			var m = parseInt(ss[1],10);
			var d = parseInt(ss[2],10);
			if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
			} else {
				return new Date();
			}
		}
	</script>
</body>
</html>
```

![image-20231015222759293](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152228404.png)

### 日期验证框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Validate DateBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>日期框验证</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>日期框验证不能为空.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-datebox" required></input>
</body>
</html>
```

![image-20231015222836442](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152228530.png)

### 日期框事件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>DateBox Events - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>日期框事件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右边日历小图标.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-datebox" data-options="onSelect:onSelect"></input>
	<div style="margin:10px 0">
		<span>选择的日期是: </span>
		<span id="result"></span>
	</div>
	<script>
		function onSelect(date){
			$('#result').text(date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate());
		}
	</script>
</body>
</html>
```

![image-20231015222903286](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152229211.png)

### 时间和日期框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic DateTimeBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本日期时间控件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右边日历小图标.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-datetimebox" required style="width:150px">
</body>
</html>
```

![image-20231015222935744](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152229074.png)

### 带有初始值的时间框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Initialize Value for DateTime - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>为日期时间组件初始化值</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>当日期时间组件创建后值被初始化.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-datetimebox" value="2013-11-11 2:3:56" style="width:150px">

</body>
</html>
```

![image-20231015223015341](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152230510.png)

### 带有秒数的日期框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Display Seconds - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>显示秒</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>用户能够决定是否显示秒部分.</div>
	</div>
	<div style="margin:10px 0;">
		<span>显示秒: </span>
		<input type="checkbox" checked onchange="$('#dt').datetimebox({showSeconds:$(this).is(':checked')})">
	</div>
	<input id="dt" class="easyui-datetimebox" style="width:150px">

</body>
</html>
```

![image-20231015223049991](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152230195.png)



## 时间微调组件

### 基本微调组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic TimeSpinner - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本时间微调器组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击微调按钮来调整时间.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-timespinner" style="width:80px;">
</body>
</html>
```

![image-20231015223130976](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152231997.png)



### 带有范围的微调组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Time Range - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>时间范围</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>时间被限制在指定范围内.</div>
	</div>
	<div style="margin:10px 0;">
		<span>08:30到18:00</span>
	</div>
	<input class="easyui-timespinner" data-options="min:'08:30',max:'18:00'" style="width:80px;"></input>
</body>
</html>
```

![image-20231015223156586](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152231661.png)

### 时间微调相关操作

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>TimeSpinner Actions - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>时间微调器相关操作</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮执行相关操作.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="#" class="easyui-linkbutton" onclick="getValue()">获取值</a>
		<a href="#" class="easyui-linkbutton" onclick="setValue()">设置值</a>
		<a href="#" class="easyui-linkbutton" onclick="disable()">禁用</a>
		<a href="#" class="easyui-linkbutton" onclick="enable()">启用</a>
	</div>
	<input id="dt" class="easyui-timespinner" style="width:80px;">
	<script>
		function getValue(){
			var val = $('#dt').timespinner('getValue');
			alert(val);
		}
		function setValue(){
			$('#dt').timespinner('setValue', '09:45');
		}
		function disable(){
			$('#dt').timespinner('disable');
		}
		function enable(){
			$('#dt').timespinner('enable');
		}
	</script>
</body>
</html>
```

![image-20231015223242452](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152232718.png)



## 日历组件

### 基本日历组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Calendar - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本日历组件</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip"></div>
		<div>点击选择一个日期.</div>
	</div>

	<div class="easyui-calendar" style="width:180px;height:180px;"></div>

</body>
</html>
```

![image-20231015224149510](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152241595.png)

### 一周的第一天

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>First Day of Week - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>一周的第一天</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>选择一周的第一天.</div>
	</div>

	<div style="margin:10px 0">
		<select onchange="$('#cc').calendar({firstDay:this.value})">
			<option value="1">周一</option>
			<option value="2">周二</option>
			<option value="3">周三</option>
			<option value="4">周四</option>
			<option value="5">周五</option>
			<option value="6">周六</option>
			<option value="0">周日</option>
		</select>
	</div>

	<div id="cc" class="easyui-calendar" style="width:180px;height:180px;"></div>
</body>
</html>
```

![image-20231015224223034](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152242268.png)

## 数字框组件

### 基本数字框组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic NumberBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本数字框组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>只能输入数字.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-numberbox" required data-options="
			onChange: function(value){
				$('#vv').text(value);
			}">
	<div style="margin:10px 0;">
		值: <span id="vv"></span>
	</div>
</body>
</html>
```

![image-20231015224310561](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152243733.png)

### 带范围的数字框组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Number Range - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数字范围</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>值被限制在10到90之间.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-numberbox" data-options="min:10,max:90,precision:2,required:true">
</body>
</html>
```

![image-20231015224346527](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152243708.png)

### 格式化数字

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Format NumberBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>格式化数字</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>数字格式化能够控制一个数字如何显示.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<table>
		<tr>
			<td>美国数字格式：</td>
			<td><input class="easyui-numberbox" value="1234567.89" data-options="precision:2,groupSeparator:','"></input></td>
		</tr>
		<tr>
			<td>法国数字格式：</td>
			<td><input class="easyui-numberbox" value="1234567.89" data-options="precision:2,groupSeparator:' ',decimalSeparator:','"></input></td>
		</tr>
		<tr>
			<td>货币:美元</td>
			<td><input class="easyui-numberbox" value="1234567.89" data-options="precision:2,groupSeparator:',',decimalSeparator:'.',prefix:'$'"></input></td>
		</tr>
		<tr>
			<td>货币:欧元</td>
			<td><input class="easyui-numberbox" value="1234567.89" data-options="precision:2,groupSeparator:',',decimalSeparator:' ',prefix:'€'"></input></td>
		</tr>
		<tr>
			<td></td>
			<td><input class="easyui-numberbox" value="1234567.89" data-options="precision:2,groupSeparator:' ',decimalSeparator:',',suffix:'€'"></input></td>
		</tr>
	</table>

</body>
</html>
```

![image-20231015224413105](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152244150.png)

### 数字框基本微调器

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic NumberSpinner - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本数字微调器组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击微调按钮来调整时间.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-numberspinner" style="width:80px;" data-options="
				onChange: function(value){
					$('#vv').text(value);
				}
			"></input>
	<div style="margin:10px 0;">
		值: <span id="vv"></span>
	</div>
</body>
</html>
```

![image-20231015224459126](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152245130.png)

### 数字框增量

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Increment Number - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>增量数</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>如何设置增量数.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-numberspinner" value="1000" data-options="increment:100" style="width:120px;"></input>
</body>
</html>
```

![image-20231015224523378](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152245577.png)

### 数字框带范围

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Number Range - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>数字范围</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>值被限制在10到90之间.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-numberbox" data-options="min:10,max:90,precision:2,required:true">
</body>
</html>
```

![image-20231015224550339](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152245364.png)

## 验证框组件

### 基本验证框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic ValidateBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本验证框组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>我们可以很方便地添加验证逻辑到文本框里.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-panel" title="注册" style="width:400px;padding:10px">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input class="easyui-validatebox" data-options="required:true,validType:'length[3,10]'"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input class="easyui-validatebox" data-options="required:true,validType:'email'"></td>
			</tr>
			<tr>
				<td>出生日期:</td>
				<td><input class="easyui-datebox"></td>
			</tr>
			<tr>
				<td>URL:</td>
				<td><input class="easyui-validatebox" data-options="required:true,validType:'url'"></td>
			</tr>
			<tr>
				<td>电话:</td>
				<td><input class="easyui-validatebox" data-options="required:true"></td>
			</tr>
		</table>
	</div>

</body>
</html>
```

![image-20231015224629221](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152246177.png)

### 定制验证框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom ValidateBox Tooltip - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>定制验证框提示</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>如何在验证框上显示另外一种提示信息.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-panel" title="注册" style="width:400px;padding:10px">
		<table>
			<tr>
				<td>用户名:</td>
				<td><input class="easyui-validatebox" data-options="prompt:'请输入您的用户名.',required:true,validType:'length[3,10]'"></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input class="easyui-validatebox" data-options="prompt:'请输入一个有效的Email.',required:true,validType:'email'"></td>
			</tr>
			<tr>
				<td>出生日期:</td>
				<td><input class="easyui-datebox"></td>
			</tr>
			<tr>
				<td>URL:</td>
				<td><input class="easyui-validatebox" data-options="prompt:'请输入您的URL.',required:true,validType:'url'"></td>
			</tr>
			<tr>
				<td>电话:</td>
				<td><input class="easyui-validatebox" data-options="prompt:'请输入您的电话号码.',required:true"></td>
			</tr>
		</table>
	</div>
	<script>
		$(function(){
			$('input.easyui-validatebox').validatebox({
				tipOptions: {	// the options to create tooltip
					showEvent: 'mouseenter',
					hideEvent: 'mouseleave',
					showDelay: 0,
					hideDelay: 0,
					zIndex: '',
					onShow: function(){
						if (!$(this).hasClass('validatebox-invalid')){
							if ($(this).tooltip('options').prompt){
								$(this).tooltip('update', $(this).tooltip('options').prompt);
							} else {
								$(this).tooltip('tip').hide();
							}
						} else {
							$(this).tooltip('tip').css({
								color: '#000',
								borderColor: '#CC9933',
								backgroundColor: '#FFFFCC'
							});
						}
					},
					onHide: function(){
						if (!$(this).tooltip('options').prompt){
							$(this).tooltip('destroy');
						}
					}
				}
			}).tooltip({
				position: 'right',
				content: function(){
					var opts = $(this).validatebox('options');
					return opts.prompt;
				},
				onShow: function(){
					$(this).tooltip('tip').css({
						color: '#000',
						borderColor: '#CC9933',
						backgroundColor: '#FFFFCC'
					});
				}
			});
		});
	</script>
</body>
</html>
```

![image-20231015224717411](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152247622.png)

## 滑动组件

### 基本滑动组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Slider - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本滑动器组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>拖动滑动器来改变值.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="margin-top:20px;">
		<input class="easyui-slider" style="width:300px" data-options="showTip:true">
	</div>
</body>
</html>
```

![image-20231015224818313](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152248283.png)



### 滑动标尺

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Slider Rule - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>滑动器标尺</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>如何定义滑动器标尺.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="margin-top:20px;">
		<input class="easyui-slider" style="width:300px" data-options="
					showTip:true,
					rule: [0,'|',25,'|',50,'|',75,'|',100]
				">
	</div>
</body>
</html>
```

![image-20231015224846535](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152248484.png)



### 带提示信息的滑动组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Format Tip Information - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>格式化提示信息</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>如何格式化提示信息.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div style="margin-top:20px;">
		<input class="easyui-slider" value="12" style="width:300px" data-options="
				showTip: true,
				rule: [0,'|',25,'|',50,'|',75,'|',100],
				tipFormatter: function(value){
					return value+'px';
				},
				onChange: function(value){
					$('#ff').css('font-size', value);
				}">
	</div>
	<div id="ff" style="margin-top:50px;font-size:12px">www.etjava.com</div>

</body>
</html>
```

![image-20231015225157170](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152251614.png)

### 垂直滑动

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Vertical Slider - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>垂直滑动器</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>如何创建一个垂直滑动器.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<input class="easyui-slider" style="height:300px" data-options="
				showTip: true,
				mode: 'v',
				reversed: false,
				rule: [0,'|',25,'|',50,'|',75,'|',100]
			">

</body>
</html>
```

![image-20231015225226814](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152252925.png)

## 下拉组件

### 基本下拉选择

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Combo - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本下拉组件</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip"></div>
		<div>点击右边箭头按钮显示下拉面板，选一个值填充到文本框内.</div>
	</div>
	<select id="cc" style="width:150px"></select>
	<div id="sp">
		<div style="color:#99BBE8;background:#fafafa;padding:5px;">选择一种语言</div>
		<input type="radio" name="lang" value="01"><span>Java</span><br/>
		<input type="radio" name="lang" value="02"><span>C#</span><br/>
		<input type="radio" name="lang" value="03"><span>Ruby</span><br/>
		<input type="radio" name="lang" value="04"><span>Basic</span><br/>
		<input type="radio" name="lang" value="05"><span>Fortran</span>
	</div>
	<script type="text/javascript">
		$(function(){
			$('#cc').combo({
				required:true,
				editable:false
			});
			$('#sp').appendTo($('#cc').combo('panel'));
			$('#sp input').click(function(){
				var v = $(this).val();
				var s = $(this).next('span').text();
				$('#cc').combo('setValue', v).combo('setText', s).combo('hidePanel');
			});
		});
	</script>
</body>
</html>
```

![image-20231015225302838](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152253025.png)

## 下拉框组件

combobox_data1.json

```json
[{
	"id":1,
	"text":"Java",
	"desc":"Write once, run anywhere"
},{
	"id":2,
	"text":"C#",
	"desc":"One of the programming languages designed for the Common Language Infrastructure"
},{
	"id":3,
	"text":"Ruby",
	"selected":true,
	"desc":"A dynamic, reflective, general-purpose object-oriented programming language"
},{
	"id":4,
	"text":"Perl",
	"desc":"A high-level, general-purpose, interpreted, dynamic programming language"
},{
	"id":5,
	"text":"Basic",
	"desc":"A family of general-purpose, high-level programming languages"
}]
```



### 基本下拉框组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic ComboBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本下拉框组件</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip"></div>
		<div>在下拉框内输入信息自动匹配.</div>
	</div>

	<select class="easyui-combobox" name="sheng" style="width:200px;">
		<option value="河北省">河北省</option>
		<option value="山东省">山东省</option>
		<option value="辽宁省">辽宁省</option>
		<option value="黑龙江省">黑龙江省</option>
		<option value="吉林省">吉林省</option>
		<option value="甘肃省">甘肃省</option>
		<option value="青海省">青海省</option>
		<option value="河南省">河南省</option>
		<option value="江苏省">江苏省</option>
		<option value="湖北省">湖北省</option>
		<option value="湖南省">湖南省</option>
		<option value="江西省">江西省</option>
		<option value="浙江省">浙江省</option>
		<option value="广东省">广东省</option>
		<option value="云南省">云南省</option>
		<option value="福建省">福建省</option>
		<option value="台湾省">台湾省</option>
		<option value="海南省">海南省</option>
		<option value="山西省">山西省</option>
		<option value="四川省">四川省</option>
		<option value="陕西省">陕西省</option>
		<option value="贵州省">贵州省</option>
		<option value="安徽省">安徽省</option>
	</select>

</body>
</html>
```

![image-20231015225511698](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152255180.png)

### 动态下拉框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Load Dynamic ComboBox Data - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>动态加载下拉框数据</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮加载数据.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#language').combobox('reload', '../combobox/combobox_data1.json')">加载数据</a>
	</div>

	<input class="easyui-combobox" id="language" name="language"
			data-options="valueField:'id',textField:'text'">
</body>
</html>
```

![image-20231015225549505](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152255217.png)



### 下拉多选框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Multiple Select - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>下拉框多选</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip"></div>
		<div>点击下拉框选择多个项.</div>
	</div>
	<input class="easyui-combobox"
			name="language"
			data-options="
					url:'../combobox/combobox_data1.json',
					valueField:'id',
					textField:'text',
					multiple:true,
					panelHeight:'auto'
			">

</body>
</html>
```

![image-20231015225621680](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152256217.png)

### 导航下拉框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Navigate ComboBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>导航下拉框</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip"></div>
		<div>导航下拉框时选中一个值.</div>
	</div>
	<div style="margin:10px 0;">
		<input type="checkbox" checked onchange="$('#cc').combobox({selectOnNavigation:$(this).is(':checked')})">
		<span>导航选中</span>
	</div>

	<select id="cc" class="easyui-combobox" name="sheng" style="width:200px;">
		<option value="河北省">河北省</option>
		<option value="山东省">山东省</option>
		<option value="辽宁省">辽宁省</option>
		<option value="黑龙江省">黑龙江省</option>
		<option value="吉林省">吉林省</option>
		<option value="甘肃省">甘肃省</option>
		<option value="青海省">青海省</option>
		<option value="河南省">河南省</option>
		<option value="江苏省">江苏省</option>
		<option value="湖北省">湖北省</option>
		<option value="湖南省">湖南省</option>
		<option value="江西省">江西省</option>
		<option value="浙江省">浙江省</option>
		<option value="广东省">广东省</option>
		<option value="云南省">云南省</option>
		<option value="福建省">福建省</option>
		<option value="台湾省">台湾省</option>
		<option value="海南省">海南省</option>
		<option value="山西省">山西省</option>
		<option value="四川省">四川省</option>
		<option value="陕西省">陕西省</option>
		<option value="贵州省">贵州省</option>
		<option value="安徽省">安徽省</option>
	</select>

</body>
</html>
```

![image-20231015225656082](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152256175.png)

### 自定义格式的下拉框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Custom Format in ComboBox - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>下拉框自定义格式</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip"></div>
		<div>如何在下拉框中自定义格式.</div>
	</div>
	<input class="easyui-combobox" name="language" data-options="
				url: '../combobox/combobox_data1.json',
				valueField: 'id',
				textField: 'text',
				panelWidth: 350,
				panelHeight: 'auto',
				formatter: formatItem
			">
	<script type="text/javascript">
		function formatItem(row){
			var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
					'<span style="color:#888">' + row.desc + '</span>';
			return s;
		}
	</script>
</body>
</html>
```

![image-20231015225724074](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152257232.png)

### 动态获取数据的下拉框

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Binding to Remote Data - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>绑定远程数据</h2>
	<div class="demo-info" style="margin-bottom:10px">
		<div class="demo-tip icon-tip"></div>
		<div>下拉框绑定了一个远程数据.</div>
	</div>
	<input class="easyui-combobox"
			name="language"
			data-options="
					url:'../combobox/combobox_data1.json',
					valueField:'id',
					textField:'text',
					panelHeight:'auto'
			">

</body>
</html>
```

![image-20231015225753750](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152257899.png)

### 下拉框组件的相关操作

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ComboBox Actions - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>下拉框组件相关操作</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮执行相关操作.</div>
	</div>

	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="setvalue()">设置值</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="alert($('#state').combobox('getValue'))">获取值</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#state').combobox('disable')">禁用</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#state').combobox('enable')">启用</a>
	</div>
	<script type="text/javascript">
		function setvalue(){
			$.messager.prompt('设置值','请输入一个值(张三,李四,王八等):',function(v){
				if (v){
					$('#state').combobox('setValue',v);
				}
			});
		}
	</script>

	<select id="state" class="easyui-combobox" name="sheng" style="width:200px;">
		<option value="河北省">河北省</option>
		<option value="山东省">山东省</option>
		<option value="辽宁省">辽宁省</option>
		<option value="黑龙江省">黑龙江省</option>
		<option value="吉林省">吉林省</option>
		<option value="甘肃省">甘肃省</option>
		<option value="青海省">青海省</option>
		<option value="河南省">河南省</option>
		<option value="江苏省">江苏省</option>
		<option value="湖北省">湖北省</option>
		<option value="湖南省">湖南省</option>
		<option value="江西省">江西省</option>
		<option value="浙江省">浙江省</option>
		<option value="广东省">广东省</option>
		<option value="云南省">云南省</option>
		<option value="福建省">福建省</option>
		<option value="台湾省">台湾省</option>
		<option value="海南省">海南省</option>
		<option value="山西省">山西省</option>
		<option value="四川省">四川省</option>
		<option value="陕西省">陕西省</option>
		<option value="贵州省">贵州省</option>
		<option value="安徽省">安徽省</option>
	</select>

</body>
</html>
```

![image-20231015225822865](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152258031.png)

## 下拉表格组件

### 数据

combobox_data1.json

```json
[{
	"id":1,
	"text":"Java",
	"desc":"Write once, run anywhere"
},{
	"id":2,
	"text":"C#",
	"desc":"One of the programming languages designed for the Common Language Infrastructure"
},{
	"id":3,
	"text":"Ruby",
	"selected":true,
	"desc":"A dynamic, reflective, general-purpose object-oriented programming language"
},{
	"id":4,
	"text":"Perl",
	"desc":"A high-level, general-purpose, interpreted, dynamic programming language"
},{
	"id":5,
	"text":"Basic",
	"desc":"A family of general-purpose, high-level programming languages"
}]
```

### 基本下拉表格

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic ComboGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本下拉表格组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右边箭头按钮来显示数据表格.</div>
	</div>
	<div style="margin:10px 0"></div>
	<select class="easyui-combogrid" style="width:250px" data-options="
			panelWidth: 500,
			idField: 'itemid',
			textField: 'productname',
			url: '../combogrid/datagrid_data1.json',
			columns: [[
				{field:'itemid',title:'产品编号',width:80},
				{field:'productname',title:'产品名称',width:120},
				{field:'listprice',title:'市场价',width:80,align:'right'},
				{field:'unitcost',title:'成本价',width:80,align:'right'},
				{field:'attr1',title:'描述',width:200},
				{field:'status',title:'状态',width:60,align:'center'}
			]],
			fitColumns: true
		">
	</select>
</body>
</html>
```

![image-20231015225955477](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152259575.png)

### 带有初始值的下拉表格

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Initialize Value for ComboGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>为下拉表格赋初值</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>当下拉表格创建完后赋初值.</div>
	</div>
	<div style="margin:10px 0"></div>
	<input class="easyui-combogrid" style="width:250px" value="EST-12" data-options="
			panelWidth: 500,
			idField: 'itemid',
			textField: 'productname',
			url: '../combogrid/datagrid_data1.json',
			columns: [[
				{field:'itemid',title:'产品编号',width:80},
				{field:'productname',title:'产品名称',width:120},
				{field:'listprice',title:'市场价',width:80,align:'right'},
				{field:'unitcost',title:'成本价',width:80,align:'right'},
				{field:'attr1',title:'描述',width:200},
				{field:'status',title:'状态',width:60,align:'center'}
			]],
			fitColumns: true
		">
</body>
</html>
```

![image-20231015230028533](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152300678.png)

### 带多选框的下拉表格

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Multiple ComboGrid - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>多选下拉表格</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右边箭头按钮显示表格数据进行选择.</div>
	</div>
	<div style="margin:10px 0"></div>
	<select class="easyui-combogrid" style="width:250px" data-options="
			panelWidth: 500,
			multiple: true,
			idField: 'itemid',
			textField: 'productname',
			url: '../combogrid/datagrid_data1.json',
			columns: [[
				{field:'ck',checkbox:true},
				{field:'itemid',title:'产品编号',width:80},
				{field:'productname',title:'产品名称',width:120},
				{field:'listprice',title:'市场价',width:80,align:'right'},
				{field:'unitcost',title:'成本价',width:80,align:'right'},
				{field:'attr1',title:'描述',width:200},
				{field:'status',title:'状态',width:60,align:'center'}
			]],
			fitColumns: true
		">
	</select>

</body>
</html>
```

![image-20231015230059059](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152301358.png)

### 下拉表格基本操作

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ComboGrid Actions - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>下拉表格相关操作</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮执行相关操作.</div>
	</div>
	<div style="margin:10px 0">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="getValue()">获取值</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="setValue()">设置值</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="disable()">禁用</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="enable()">启用</a>
	</div>
	<input id="cc" class="easyui-combogrid" style="width:250px" data-options="
			panelWidth: 500,
			idField: 'itemid',
			textField: 'productname',
			url: '../combogrid/datagrid_data1.json',
			columns: [[
				{field:'itemid',title:'产品编号',width:80},
				{field:'productname',title:'产品名称',width:120},
				{field:'listprice',title:'市场价',width:80,align:'right'},
				{field:'unitcost',title:'成本价',width:80,align:'right'},
				{field:'attr1',title:'描述',width:200},
				{field:'status',title:'状态',width:60,align:'center'}
			]],
			fitColumns: true
		">
	<script type="text/javascript">
		function getValue(){
			var val = $('#cc').combogrid('getValue');
			alert(val);
		}
		function setValue(){
			$('#cc').combogrid('setValue', 'EST-13');
		}
		function disable(){
			$('#cc').combogrid('disable');
		}
		function enable(){
			$('#cc').combogrid('enable');
		}
	</script>
</body>
</html>
```

![image-20231015230133396](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152301815.png)

## 下拉树组件

### 数据

combobox_data1.json

```json
[{
	"id":1,
	"text":"Java",
	"desc":"Write once, run anywhere"
},{
	"id":2,
	"text":"C#",
	"desc":"One of the programming languages designed for the Common Language Infrastructure"
},{
	"id":3,
	"text":"Ruby",
	"selected":true,
	"desc":"A dynamic, reflective, general-purpose object-oriented programming language"
},{
	"id":4,
	"text":"Perl",
	"desc":"A high-level, general-purpose, interpreted, dynamic programming language"
},{
	"id":5,
	"text":"Basic",
	"desc":"A family of general-purpose, high-level programming languages"
}]
```

### 基本下拉树组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic ComboTree - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本下拉树组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右边箭头按钮来显示树数据.</div>
	</div>
	<div style="margin:10px 0"></div>
	<input class="easyui-combotree" data-options="url:'../combotree/tree_data1.json',required:true" style="width:200px;">

</body>
</html>
```

![image-20231015230237292](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152302812.png)

### 带初始值的下拉树组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Initialize Value for ComboTree - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>为下拉树赋初值</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>当下拉树创建完后赋初值.</div>
	</div>
	<div style="margin:10px 0"></div>
	<input class="easyui-combotree" value="122" data-options="url:'../combotree/tree_data1.json',required:true" style="width:200px;">

</body>
</html>
```

![image-20231015230325955](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152303209.png)



### 带选择框的下拉树组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Multiple ComboTree - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>多选下拉树</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击右边箭头按钮显示树数据进行选择.</div>
	</div>
	<div style="margin:10px 0">
		<span>级联选择: </span>
		<input type="checkbox" checked onclick="$('#cc').combotree({cascadeCheck:$(this).is(':checked')})">
	</div>
	<select id="cc" class="easyui-combotree" data-options="url:'../combotree/tree_data1.json'" multiple style="width:200px;"></select>

</body>
</html>
```

![image-20231015230412433](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152304584.png)



### 下拉树相关操作

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ComboTree Actions - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>下拉树操作</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮执行相关操作</div>
	</div>
	<div style="margin:10px 0">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="getValue()">获取值</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="setValue()">设置值</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="disable()">禁用</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="enable()">启用</a>
	</div>
	<input id="cc" class="easyui-combotree" data-options="url:'../combotree/tree_data1.json',required:true" style="width:200px;">
	<script type="text/javascript">
		function getValue(){
			var val = $('#cc').combotree('getValue');
			alert(val);
		}
		function setValue(){
			$('#cc').combotree('setValue', '122');
		}
		function disable(){
			$('#cc').combotree('disable');
		}
		function enable(){
			$('#cc').combotree('enable');
		}
	</script>

</body>
</html>
```

![image-20231015230442825](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152304872.png)

## 表单组件

### 基本表单组件

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Basic Form - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>基本表单组件</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>填写表单,然后提交.</div>
	</div>
	<div style="margin:10px 0;"></div>
	<div class="easyui-panel" title="发表主题" style="width:400px">
		<div style="padding:10px 0 10px 60px">
	    <form id="ff" action="form" method="post" >
	    	<table>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>Email:</td>
	    			<td><input class="easyui-validatebox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
	    		</tr>
	    		<tr>
	    			<td>主题:</td>
	    			<td><input class="easyui-validatebox" type="text" name="subject" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>内容:</td>
	    			<td><textarea name="message" style="height:60px;"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td>语言:</td>
	    			<td>
	    				<select class="easyui-combobox" name="language"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	    </div>
	</div>
	<script>
		function submitForm(){
			$('#ff').form('submit');
		}
		function clearForm(){
			$('#ff').form('clear');
		}
	</script>
</body>
</html>
```

![image-20231015230542652](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152305749.png)

### 加载表单数据

form_data1.json

```html
{
	"name":"lisi",
	"email":"lisi@qq.com",
	"subject":"lisi subject",
	"message":"lisi message",
	"language":"en"
}
```

```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Load Form Data - jQuery EasyUI Demo</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<h2>加载表单数据</h2>
	<div class="demo-info">
		<div class="demo-tip icon-tip"></div>
		<div>点击下面按钮加载数据.</div>
	</div>
	<div style="margin:10px 0;">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="loadLocal()">加载本地</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="loadRemote()">加载远程</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
	</div>
	<div class="easyui-panel" title="发表主题" style="width:400px">
		<div style="padding:10px 0 10px 60px">
	    <form id="ff" method="post">
	    	<table>
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>Email:</td>
	    			<td><input class="easyui-validatebox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
	    		</tr>
	    		<tr>
	    			<td>主题:</td>
	    			<td><input class="easyui-validatebox" type="text" name="subject" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>内容:</td>
	    			<td><textarea name="message" style="height:60px;"></textarea></td>
	    		</tr>
	    		<tr>
	    			<td>语言:</td>
	    			<td>
	    				<select class="easyui-combobox" name="language"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
	    			</td>
	    		</tr>
	    	</table>
	    </form>
	    </div>
	</div>
	<script>
		function loadLocal(){
			$('#ff').form('load',{
				name:'张三',
				email:'zhangsan@qq.com',
				subject:'张三的主题',
				message:'张三的内容',
				language:'en'
			});
		}
		function loadRemote(){
			$('#ff').form('load', '../form/form_data1.json');
		}
		function clearForm(){
			$('#ff').form('clear');
		}
	</script>
</body>
</html>
```

![image-20231015230643781](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152306654.png)

### Ajax提交表单

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="keywords" content="jquery,ui,easy,easyui,web">
    <meta name="description" content="easyui help you build your web page easily!">
    <title>Ajax Form - jQuery EasyUI Demo</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/demo/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
    <h2>AJAX表单提交</h2>
    <div class="demo-info" style="margin-bottom:10px">
        <div class="demo-tip icon-tip">&nbsp;</div>
        <div>填写表单,然后提交.</div>
    </div>

    <div class="easyui-panel" title="Ajax表单" style="width:230px;padding:10px;">
        <form id="ff" action="form" method="post">
            <table>
                <tr>
                    <td>姓名:</td>
                    <td><input name="name" type="text"></input></td>
                </tr>
                <tr>
                    <td>Email:</td>
                    <td><input name="email" type="text"></input></td>
                </tr>
                <tr>
                    <td>电话</td>
                    <td><input name="phone" type="text"></input></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="提交"></input></td>
                </tr>
            </table>
        </form>
    </div>
    <script type="text/javascript">
        $(function(){
            $('#ff').form({
                success:function(data){
                    $.messager.alert('系统提示', data, 'info');
                }
            });
        });
    </script>
</body>
</html>
```

![image-20231015230902289](https://cdn.jsdelivr.net/gh/etjava/TyporaPIC/img/202310152309905.png)



Servlet

```java
package com.etjava.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormServlet extends HttpServlet{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		System.out.println("姓名："+request.getParameter("name"));
		System.out.println("Email："+request.getParameter("email"));
		System.out.println("电话："+request.getParameter("phone"));

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println("ajax提交成功");
		out.flush();
		out.close();
	}

}
```











