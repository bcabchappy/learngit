<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
<%
request.setCharacterEncoding("utf-8");
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path;
String agsupportUrl = "http://192.168.31.66:8083/dgsp";
String arcgisJsApiUrl = "http://112.74.13.45:8091/arcgis_js_v316";
String arcgisServerUrl = "http://192.168.15.83:6080";
String themeName = "default";

long userId = 0;
String userName = "超级管理员";
String loginName = "agcomadmin";
String departName = "厦门市";
%>
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>百度地图展示平台</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" href="<%=arcgisJsApiUrl%>/api/esri/css/esri.css">
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="<%=basePath%>/resources/easyui/themes/<%=themeName%>/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/resources/css/<%=themeName%>/main.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/resources/css/imgareaselect/imgareaselect-default.css" />

<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=QDE1Bn3SWHhurwaPIdb1KG2Q"></script>
<script src="<%=basePath%>/resources/jquery/jquery-1.11.2.min.js"></script>
<script src="<%=basePath%>/resources/jquery/jquery.cookie.js"></script>
<script src="<%=basePath%>/resources/jquery/jstree/jstree.min.js"></script>
<script src="<%=basePath%>/resources/easyui/jquery.easyui.min.js"></script>
<script src="<%=basePath%>/resources/easyui/locale/easyui-lang-zh_CN.js"></script>
<script src="<%=arcgisJsApiUrl%>/api/config.js"></script>
<script src="<%=arcgisJsApiUrl%>/api/init.js"></script>
<script src="<%=basePath%>/module/index/data.js"></script>
<script src="<%=basePath%>/module/cutMap/jquery.json-2.3.min.js"></script>
<script src="<%=basePath%>/module/cutMap/niuniucapture.js"></script>
<script src="<%=basePath%>/module/cutMap/capturewrapper.js"></script>
<script src="<%=basePath%>/module/common/MapUtils.js"></script>
<script src="<%=basePath%>/module/toolbar/toolbar.js"></script>
<script
	src="<%=basePath%>/resources/js/imgareaselect/jquery.imgareaselect.pack.js"></script>
<script src="<%=basePath%>/module/common/DataGridCellTip.js"></script>
<script src="<%=basePath%>/module/common/customDatagrid.js"></script>
<script src="<%=basePath%>/module/multiscreen/multiscreen.js"></script>
<script src="./agMpiMap/bdMap/js/BDAnoLayer.js"></script>
<script src="./agMpiMap/bdMap/js/BDImgLayer.js"></script>
<script src="./agMpiMap/bdMap/js/BDVecLayer.js"></script>
<script src="./agMpiMap/bdMap/js/GpsConvert.js"></script>

<style>
html,body {
	height: 100%;
	margin: 0;
	padding: 0;
}

.base-map-ano {
	position: absolute;
	right: 0pt;
	top: 18pt;
	background: #e6edf1;
	border: #96aed1 1px solid;
	padding: 4px 5px;
	padding-left: 0px;
	padding-top: 0px;
	display: none;
	font-weight: normal;
}

.base-map {
	position: absolute;
	right: 15pt;
	top: 15pt;
	background: #f0f0f0;
	border: #96aed1 1px solid;
	width: auto;
	height: auto;
	z-index: 99;
	font: normal 11px "宋体", Arial;
	color: #868686;
}

.base-map-switch {
	padding: 4px 8px;
	float: left;
}

.base-map-switch-active {
	background: #e6edf1;
	font-weight: bold;
	color: #4d4d4d;
}

.base-map-switch:hover {
	cursor: pointer;
}

.base-map-switch-center {
	border: 1px #96aed1 solid;
	border-top: none;
	border-bottom: none;
}
</style>

<script type="text/javascript">
<%--        dojoConfig = {--%>
<%--            parseOnLoad: true,--%>
<%--             packages: [{--%>
<%--                 name: 'bdlib',--%>
<%--                 location: "http://112.74.13.45:8091/arcgis_js_v316/api/bdlib"--%>
<%--             }]--%>
<%--        };--%>
    </script>
<script>
    var vecMap;
    var imgMap;
    var anoMap;
     
       function showMap(layer){
            //设置按钮样式
            var baseMap = ["vec","img"];
            for(var i= 0, dl=baseMap.length;i<dl;i++){
                $("#"+baseMap[i]).removeClass("base-map-switch-active");
            }
            $("#"+layer).addClass("base-map-switch-active");
            //设置显示地图
            switch(layer){
                case "img":{//影像
                    vecMap.hide();
                    imgMap.show();
                    $("#ano").show();
                    break;
                }
                default :{//地图
                    vecMap.show();
                    imgMap.hide(),anoMap.hide();
                    $("#ano").hide();
                    $("#chkAno").attr("checked",false);
                    break;
                }
            }
        }
       
       function anoCtrl(){
           if($("#chkAno").attr("checked")){
               anoMap.show();
           }
           else{
               anoMap.hide();
           }
       }
       
    </script>


<script>
		var path = '<%=basePath%>';
		var baseRestUrl = '<%=agsupportUrl%>';
		var arcgisUrl = '<%=arcgisServerUrl%>';
		var arcgisApiUrl = '<%=arcgisJsApiUrl%>';
		var userId = '<%=userId%>';
		var userName = '<%=userName%>';
		var departName = '<%=departName%>';
		var pam = null;
		var mapParam;
		var agsMap;  
        var tiledMapLayer;
        var mapToolBar;
        
		function createTiledLayer(url){
			return new esri.layers.ArcGISTiledMapServiceLayer(url);
		}
		
		function createFeatureLayer(url){
			return new esri.layers.FeatureLayer(url);
		}
		
		function createDynamicLayer(url){
			return new esri.layers.ArcGISDynamicMapServiceLayer(url);
		}
		
		function createGraphicsLayer(){
			return new esri.layers.GraphicsLayer();
		}
		
		function hideLeftPanel(b){
			document.getElementById("dghy_index_left").contentWindow.hideLeftPanel(b);
		}
		
		////
		function drawPoint(){
			activateTool();
		}
		function createToolbar(agsMap) {
		    toolbar = new esri.toolbars.Draw(agsMap);
		    toolbar.on("draw-end", addToMap);
		}
		
		function activateTool() {
            //var tool = this.label.toUpperCase().replace(/ /g, "_");
            var tool="POINT";
            toolbar.activate(esri.toolbars.Draw[tool]);
            agsMap.hideZoomSlider();
          }
		function addToMap(evt) {
            var symbol;
            toolbar.deactivate();
            agsMap.showZoomSlider();
            switch (evt.geometry.type) {
              case "point":
              case "multipoint":
                symbol = new esri.symbol.SimpleMarkerSymbol();
                break;
              case "polyline":
                symbol = new esri.symbol.SimpleLineSymbol();
                break;
              default:
                symbol = new esri.symbol.SimpleFillSymbol();
                break;
            }
            agsMap.graphics.clear();
            var graphic = new esri.Graphic(evt.geometry, symbol);
            document.getElementById("zbDraowLabel").innerHTML=""+graphic.geometry.x+","+graphic.geometry.y;
            agsMap.graphics.add(graphic);
          }
		
		function loadMap() {
			if(!agsMap){
				agsMap = new esri.Map("main", {logo: false, slider: false}); 
				agsMap.spatialReference=new esri.SpatialReference({ wkid: 102100 });
			}
			var handle=dojo.connect(agsMap,"onClick",function(event) {
				 var p=event.mapPoint;
				 document.getElementById("zbLabel").innerHTML=""+agsMap.getLevel()+":"+""+p.x+","+p.y;
			});
			
			//dojo.byId("drawp")
			//var handle1=dojo.connect(dijit.byId("drawp"),"onClick",function(event) {
				 //document.getElementById("zbLabel").innerHTML=""+p.x+","+p.y;
				// activateTool();
			//});
			createToolbar(agsMap);
			
			return;
			
		    var ydtUrl="http://192.168.15.83:6080/arcgis/rest/services/CoreMap/xm_sghy_ydt/MapServer";
		    var jsxmUrl="http://192.168.15.83:6080/arcgis/rest/services/CoreMap/xm_sghy_jsxm/MapServer";
		    var ylydUrl="http://192.168.15.83:6080/arcgis/rest/services/ZxghMap/xm_zxgh_ylws/MapServer";
		    var layer = esri.layers.ArcGISTiledMapServiceLayer(ydtUrl);
			layer.id = "ydt";
			agsMap.addLayer(createTiledLayer(ydtUrl));
			agsMap.addLayer(createDynamicLayer(jsxmUrl));
			agsMap.addLayer(createDynamicLayer(ylydUrl));
		    dojo.connect(agsMap, 'onLoad', function (theMap) {
		    	headerInit();
				dojo.connect(dijit.byId('main'), 'resize', agsMap, agsMap.resize);
				//mapToolBar = new MapToolBar(agsMap);
				//mapToolBar.createNavigation();
				//multiscreen.mainMap = agsMap;
				//var point = esri.geometry.Point(mapParam.mapXY.split(",")[0],mapParam.mapXY.split(",")[1],agsMap.spatialReference);
			    //agsMap.setLevel(mapParam.zoom);
			    //agsMap.centerAt(point);
		    });
		    //document.getElementById("dghy_index_left").src = path + "/module/index/index_left.jsp";
		    document.getElementById("dghy_index_left").onload = function(){
		    	mapToolBar = new MapToolBar(agsMap);
				mapToolBar.createNavigation();
		    }

			//百度地图
			var vecMap = new BDVecLayer();
			var imgMap = new BDImgLayer();
			var anoMap = new BDAnoLayer();
			agsMap.addLayer(vecMap);
			agsMap.addLayers([imgMap,anoMap]);
			
			
		}
		
		function showAgMap(){
			if(!agsMap){
				agsMap = new esri.Map("main", {logo: false, slider: false}); 
				agsMap.spatialReference=new esri.SpatialReference({ wkid: 102100 });
			}
			 var ydtUrl="http://192.168.15.83:6080/arcgis/rest/services/CoreMap/xm_sghy_ydt/MapServer";
			 var jsxmUrl="http://192.168.15.83:6080/arcgis/rest/services/CoreMap/xm_sghy_jsxm/MapServer";
			 var ylydUrl="http://192.168.15.83:6080/arcgis/rest/services/ZxghMap/xm_zxgh_ylws/MapServer";
			 var jsxm="http://192.168.15.83:6080/arcgis/rest/services/test/wgs84Spxmbjtc/MapServer";
			 //var layer = esri.layers.ArcGISTiledMapServiceLayer(ydtUrl);
			//agsMap.addLayer(createTiledLayer(ydtUrl));
			//agsMap.addLayer(createDynamicLayer(jsxmUrl));
			
			var url="http://192.168.15.83:6080/arcgis/rest/services/test/c1021dbd/MapServer";
			var bdqx="http://192.168.15.83:6080/arcgis/rest/services/test/bd_qx/MapServer";
			var bddrawp="http://192.168.15.83:6080/arcgis/rest/services/test/bd_drawpoint/MapServer";
			var dmdz4="http://192.168.15.83:6080/arcgis/rest/services/test/wgs84Dmdz4/MapServer";
			var bddrawp3="http://192.168.15.83:6080/arcgis/rest/services/test/bd_drawp3/MapServer";
			var bddrawp31="http://192.168.15.83:6080/arcgis/rest/services/test/bd_drawp3_1/MapServer";
			var xm_qx_jy="http://192.168.15.83:6080/arcgis/rest/services/test/xm_qx_jy/MapServer";
			var xm_qx_jy1="http://192.168.15.83:6080/arcgis/rest/services/test/xm_qx_jy1/MapServer";
			var dmdz_jy="http://192.168.15.83:6080/arcgis/rest/services/test/dmdz_jy/MapServer";
			var dmdz_1021="http://192.168.15.83:6080/arcgis/rest/services/test/dmdz_10210/MapServer";
			var spxm_jy="http://192.168.15.83:6080/arcgis/rest/services/test/spxm_yz/MapServer";
			var spxm_yz="http://192.168.15.83:6080/arcgis/rest/services/test/old_spxm_yz/MapServer";
			var xm_qx_jy_tiled="http://192.168.15.83:6080/arcgis/rest/services/test/xm_qx_jy_tiled/MapServer";
			agsMap.addLayer(createDynamicLayer(spxm_yz));
			//agsMap.addLayer(createDynamicLayer(bdqx));
			//agsMap.addLayer(createDynamicLayer(bddrawp));
			//agsMap.addLayer(createDynamicLayer(url));
			//agsMap.addLayer(createDynamicLayer(bddrawp31));
			agsMap.addLayer(createDynamicLayer(dmdz4));
			agsMap.addLayer(createDynamicLayer(dmdz_1021));
			//agsMap.addLayer(createDynamicLayer(xm_qx_jy1));
			agsMap.addLayer(createDynamicLayer(dmdz_jy));
			agsMap.addLayer(createTiledLayer(xm_qx_jy_tiled));
			
			return;
			
			agsMap.removeAllLayers();
			var layer=createDynamicLayer(dmdz4);
			layer.id="dmdz";
			agsMap.addLayer(layer);
			var str="";
			str=agsMap.layerIds.join(",");
			for(var index in agsMap.layerIds){
			}
	    }
		
		function showBdMap(){
			require(["esri/map",
			          "bdlib/BDVecLayer",
			          "bdlib/BDImgLayer",
			          "bdlib/BDAnoLayer",
			         "esri/layers/FeatureLayer",
			         "esri/geometry/Point",
			         "esri/SpatialReference",
			         "dojo/domReady!"],
			             function (Map,
			                       BDVecLayer,
			                       BDImgLayer,
			                       BDAnoLayer,
			                       FeatureLayer,
			                       Point,
			                       SpatialReference
			                     ){
								if(!agsMap){
									agsMap = new esri.Map("main", {logo: false, slider: false}); 
									agsMap.spatialReference=new esri.SpatialReference({ wkid: 102100 });
								}
			                  vecMap = new BDVecLayer();
			                  imgMap = new BDImgLayer();
			                  anoMap = new BDAnoLayer();
			                  agsMap.addLayer(vecMap);
			                  agsMap.addLayers([imgMap,anoMap]);
			                 imgMap.hide(),anoMap.hide();

			                 //var pt = new Point(7038512.810510807, 2629489.7975553474, new SpatialReference({ wkid: 102100 }));
			                 var pt = new Point(7038512.810510807, 2629489.7975553474, new SpatialReference({ wkid: 102100 }));
			                 agsMap.centerAndZoom(pt, 5);
			  });
	    }
		
		function queryTask(){
			
			//var gcg02=GPS.gcj_encrypt(39.957,116.379);//WGS-84 to GCJ-02
			//var bd09=GPS.bd_encrypt(gcg02.lon, gcg02.lat);
			
			var graphic;
			var p;
			var pt = new esri.geometry.Point(7538512.810510807, 2629489.7975553474, new esri.SpatialReference({ wkid: 102100 }));
			//pt = new esri.geometry.Point(bd09.lon,bd09.lat, new esri.SpatialReference({ wkid: 102100 }));
			// agsMap.centerAndZoom(pt, 5);
           //agsMap.removeLayer(layer);
           var graphicsLayer=agsMap.getLayer("graphicsLayer");
           if(!graphicsLayer){
        	   graphicsLayer=createGraphicsLayer();
        	   agsMap.addLayer(graphicsLayer);
           }
           graphicsLayer.clear();
           //图片定位点
			var iconUrl = 'http://192.168.31.66:8083/agMpiMap/resources/images/marker/location.png';
			var pictureMarkerSymbol = new esri.symbol.PictureMarkerSymbol(iconUrl,25,34);
			//红圆绿框
			var markerSymbol = new esri.symbol.SimpleMarkerSymbol();
			var outline = new esri.symbol.SimpleLineSymbol().setWidth(2).setColor(new esri.Color([0,255,0]));
			markerSymbol.setOutline(outline).setColor(new esri.Color([255,0,0])).setSize("32");
			//绿方红框
			var simpleMarkerSymbol= new esri.symbol.SimpleMarkerSymbol(esri.symbol.SimpleMarkerSymbol.STYLE_SQUARE, 10,
				    new esri.symbol.SimpleLineSymbol("solid",
				    new esri.Color([255,0,0]), 1),
				    new esri.Color([0,255,0,0.25]));
			//浅蓝气泡标识
			var markerSymbol1 =  new esri.symbol.SimpleMarkerSymbol();
			markerSymbol1.setPath("M16,4.938c-7.732,0-14,4.701-14,10.5c0,1.981,0.741,3.833,2.016,5.414L2,25.272l5.613-1.44c2.339,1.316,5.237,2.106,8.387,2.106c7.732,0,14-4.701,14-10.5S23.732,4.938,16,4.938zM16.868,21.375h-1.969v-1.889h1.969V21.375zM16.772,18.094h-1.777l-0.176-8.083h2.113L16.772,18.094z");
			markerSymbol1.setColor(new esri.Color("#00FFFF"));
			
			var fillSymbol = new esri.symbol.SimpleFillSymbol(esri.symbol.SimpleFillSymbol.STYLE_SOLID,
				    new esri.symbol.SimpleLineSymbol(esri.symbol.SimpleLineSymbol.STYLE_DASHDOT,
				    new esri.Color([255,0,0]), 2),new esri.Color([255,255,0,0.25])
				  );
			
			var textSymbol =  new esri.symbol.TextSymbol("Hello World");
<%--			.setColor(--%>
<%--				    new esri.Color([128,0,0])).setAngle(45).setFont(--%>
<%--				    new esri.Font("12pt").setWeight(Font.WEIGHT_BOLD)) ;--%>
<%--			--%>
			//ag
<%--			var agp = new esri.geometry.Point(118.12156709982055 ,24.55548970362707);		--%>
<%--			var graphic = new esri.Graphic(agp,pictureMarkerSymbol,null);--%>
<%--			graphic.setSymbol(pictureMarkerSymbol);--%>
<%--			graphicsLayer.add(graphic);--%>
<%--			graphicsLayer.id="graphicsLayer";--%>
<%--			//agsMap.addLayers([]);--%>
<%--			agsMap.reorderLayer(graphicsLayer, agsMap.layerIds.length+1);--%>
<%--			agsMap.centerAndZoom(agp,1);--%>
			//百度
			//pt = new esri.geometry.Point(7849823.2133, 1665341.2433999995, new esri.SpatialReference({ wkid: 102100 }));
			//pt = new esri.geometry.Point(13146934.247, 2827156.285, new esri.SpatialReference({ wkid: 102100 }));
			//var graphic = new esri.Graphic(pt,pictureMarkerSymbol,null);
			//graphicsLayer.add(graphic);
			//graphicsLayer.id="graphicsLayer";
			//agsMap.addLayers([]);
			//agsMap.reorderLayer(graphicsLayer, agsMap.layerIds.length+1);
			//agsMap.centerAndZoom(pt,5);
			//var str="";
			//str=agsMap.layerIds.join(",");
			//graphicsLayer=agsMap.getLayer("graphicsLayer");
			//var gArr=graphicsLayer.graphics;
			//return;
			
			var dmdz_xm92="http://192.168.15.83:6080/arcgis/rest/services/BaseMap/xm_sghy_dmdz/MapServer/0";
			var lineUrl="http://192.168.15.83:6080/arcgis/rest/services/BaseMap/xm_sghy_dlxt/MapServer/2";//OBJECTID<400
			var polygonUrl="http://192.168.15.83:6080/arcgis/rest/services/ApvlMap/xm_sghy_spxmbjtc/MapServer/0";
			var spxm="http://192.168.15.83:6080/arcgis/rest/services/test/old_spxm/MapServer/0";
			var queryTask = new esri.tasks.QueryTask(spxm);
			var query = new esri.tasks.Query();
			query.returnGeometry = true;
			query.outFields = ["*"];
			query.where = "FID<80";
			queryTask.execute(query,function(result){
				var features = result.features;
				if(features.length > 0){
					var bdPointArr=[];
					for(var i=0;i<features.length;i++){
						var f=features[i];
						if(f.attributes["FID"]=='78'){
							var geo=f.geometry;
							if(geo.type=="point"){
								p = new esri.geometry.Point(geo.x+7391308.319 ,geo.y-1041012.69, new esri.SpatialReference({ wkid: 102100 }));
								textSymbol.setText(f.attributes["FID"]);
								var graphic = new esri.Graphic(p,textSymbol,null);
								
							}else if(geo.type=="polyline"){
								var geoLine=getLine(geo);
								p = new esri.geometry.Point(geoLine.paths[0][0][0] ,geoLine.paths[0][0][1], new esri.SpatialReference({ wkid: 102100 }));
							   graphic = new esri.Graphic(geoLine,outline,null);
							}else if(geo.type=="polygon"){
								var geoPolygon=getPolygon(geo);
								p = new esri.geometry.Point(geoPolygon.rings[0][0][0] ,geoPolygon.rings[0][0][1], new esri.SpatialReference({ wkid: 102100 }));
							   graphic = new esri.Graphic(geoPolygon,fillSymbol,null);
							}
							
							
							graphicsLayer.add(graphic);
							//agsMap.centerAt(p);
							agsMap.centerAndZoom(p,14);
							//break;
						}
					}
<%--					$.each(features,function(index,feature){--%>
<%--						var p=feature.geometry;--%>
<%--						bdPointArr.push(new BMap.Point(p.x,p.y));--%>
<%--						setTimeout(function(){--%>
<%--							--%>
<%--					        var convertor = new BMap.Convertor();--%>
<%--					        var pointArr = [];--%>
<%--					        pointArr.push(ggPoint);--%>
<%--					        new BMap.Convertor().translate(pointArr, 1, 5, translateCallback);--%>
<%--					    }, 100);--%>
<%--					});--%>
<%--					var markergg = new BMap.Marker(bdPointArr[0]);--%>
<%--			    	 agmap.addOverlay(markergg); //添加GPS marker--%>
<%--			    	 var labelgg = new BMap.Label("未转换的GPS坐标",{offset:new BMap.Size(20,-10)});--%>
<%--			    	 markergg.setLabel(labelgg); //添加GPS label--%>
			    	    
			    	//pointsArray=wareGpsPointsBeforeSend(bdPointArr);
			    	//myTransMore(pointsArray[posIndex],0,"callback1");
			    	
				}
				else{
					return;
				}

			},
			function(error){
				alert("aa");
			});
		}
		
		function getLine(geo){
			var arr=geo.paths[0];
			var lineArr=[];
			var geoLine=new esri.geometry.Polyline(new esri.SpatialReference({ wkid: 102100 }));
			for (var i=0;i<arr.length;i++){
				var point=arr[i];
				var p = new esri.geometry.Point(point[0]+7391308.319 ,point[1]-1041012.69, new esri.SpatialReference({ wkid: 102100 }));
				lineArr.push(p);
			}
			geoLine.addPath(lineArr);
			return geoLine;
		}
		
		function getPolygon(geo){
			var arr=geo.rings[0];
			var lineArr=[];
			var geoPolygon=new esri.geometry.Polygon(new esri.SpatialReference({wkid:102100}));
			for (var i=0;i<arr.length;i++){
				var point=arr[i];
				lineArr.push([point[0]+7391308.319,point[1]-1041012.69]);
			}
			geoPolygon.addRing(lineArr);
			return geoPolygon;
		}
		
		 //坐标转换完之后的回调函数
	    translateCallback = function (data){
	      if(data.status === 0) {
	        var marker = new BMap.Marker(data.points[0]);
	        agmap.addOverlay(marker);
	        var label = new BMap.Label("转换后的百度坐标（正确）",{offset:new BMap.Size(20,-10)});
	        marker.setLabel(label); //添加百度label
	        agmap.setCenter(data.points[0]);
	      }
	    };
	    
	  //测试批量转换
	    var testJsonStr = [ //1000条数据
	     {"deviceId":"0001", "name": "0001", "longitude":116.174008, "latitude":40.059728, "time":"2015-12-18 16:19:51"},
	     {"deviceId":"0002", "name": "0002", "longitude":116.172708, "latitude":40.0603688, "time":"2015-12-18 15:44:36"},
	     {"deviceId":"0003", "name": "0003", "longitude":116.174535, "latitude":40.059727, "time":"2015-12-18 09:31:19"},
	     {"deviceId":"1000", "name": "1000", "longitude":116.37391967068, "latitude":39.981656, "time":"2015-12-18 16:59:34"} ]
	    
	    var mapcenterPt=new BMap.Point(116.403963,39.915112);
	    var posIndex=0;
	    var pointsArray=new Array();
	    var maxCnt=10;
	    
	    function TransGPS(){
	    	gpsPoints=getPoints(testJsonStr);
	    	var str="";
	    	for (var m=0;m<gpsPoints.length;m++){
	    		str+=gpsPoints[m].lng+","+gpsPoints[m].lat+";";
	    	}
	    	 var markergg = new BMap.Marker(gpsPoints[0]);
	    	 agmap.addOverlay(markergg); //添加GPS marker
	    	 var labelgg = new BMap.Label("未转换的GPS坐标:"+str,{offset:new BMap.Size(20,-10)});
	    	 markergg.setLabel(labelgg); //添加GPS label
	    	    
	    	pointsArray=wareGpsPointsBeforeSend(gpsPoints);
	    	for (var i=0;i<pointsArray.length;i++){
	    		myTransMore(pointsArray[i],0,"callback1");
	    	}
	    }
	    
	    //返回百度点数据
	    function getPoints(testJsonStr){
	    	var points=[];
	    	for(var i=0;i<testJsonStr.length;i++){
	    		if(testJsonStr[i]["longitude"]!=null &&
	        			testJsonStr[i]["longitude"]!=0 &&
	        			testJsonStr[i]["latitude"]!=null &&
	        			testJsonStr[i]["latitude"]!=0){
	    			var pt=new BMap.Point(testJsonStr[i]["longitude"],testJsonStr[i]["latitude"]);
	    			points.push(pt);
	    		}else{
	    			points.push(mapCenterPt);
	    		}
	    	}
	    	return points;
	    }
	    
	    //返回百度点二维数据。针对大数据量，将数据以100位单位拆分为二维数组
	    function wareGpsPointsBeforeSend(gpsPoints){
	    	var pointsArray=new Array();
	    	var times=Math.floor(gpsPoints.length/maxCnt);
	    	var k=0;
	    	for(var i=0;i<times;i++){
	    		pointsArray[i]=new Array();
	    		for(var j=0;j<maxCnt;j++,k++){
	    			pointsArray[i][j]=gpsPoints[k];
	    		}
	    	}
	    	if(k<gpsPoints.length){
	    		var j=0;
	    		var i=times;
	    		pointsArray[i]=new Array();
	    		while(k<gpsPoints.length){
	    			pointsArray[i][j]=gpsPoints[k];
	    			k++;
	    			j++;
	    		}
	    	}
	    	return pointsArray;
	    }
	    
	    function myTransMore(points,type,callbackName){
	    	var xyUrl="http://api.map.baidu.com/geoconv/v1/?coords=";
	    	var coordsStr="";
	    	var send=function(){
	    		var positionUrl=xyUrl+coordsStr+"&from1=&to=5&ak=ABfb51da799b942f910a4d1b9cacab24&callback="+callbackName;
	    		var script=document.createElement("script");
	    		script.src=positionUrl;
	    		document.getElementsByTagName("head")[0].appendChild(script);
	    		coordsStr="";
	    	};
	    	for(var index in points){
	    		if(index % maxCnt == 0 && index != 0){
	    			send();
	    		}
	    		coordsStr=coordsStr+points[index].lng+","+points[index].lat;
	    		if(index<points.length-1){
	    			coordsStr=coordsStr+";";
	    		}
	    		if(index == points.length-1){
	    			send();
	    		}
	    		
	    	}
	    }
	    
	    function callback1(data){
	    	if(data.status!=0){
	    		alert("地图坐标转换出错");
	    		return;
	    	}
	    	var points=data.result;
	    	var TransResult=null;
	    	var str="";
	    	var bdPoint=[];
	    	for(var index in points){
	    		TransResult=points[index];
	    		var point=new BMap.Point(TransResult.x,TransResult.y);
	    		index1=eval(Math.floor(posIndex*maxCnt)+Math.floor(index));
	    		str+=point.lng+","+point.lat+";";
	    		bdPoint.push(point);
	    		continue;
	    		if(testJsonStr[index1]["longitude"]!=null &&
	    			testJsonStr[index1]["longitude"]!=0 &&
	    			testJsonStr[index1]["latitude"]!=null &&
	    			testJsonStr[index1]["latitude"]!=0
	    			){
	    			testJsonStr[index1]["longitude"]=point.lng;
	    			testJsonStr[index1]["latitude"]=point.lat;
	    			
	    			str+=point.lng+","+point.lat+";";
	    			bdPoint.push(point);
	    		}
	    	}
	    	posIndex++;
	    	if(posIndex<pointsArray.length){
	    		myTransMore(pointsArray[posIndex],0,"callback1");
	    	}
	    	addPoint(pointsArray);
	    	if(posIndex==pointsArray.length){
	    		
	    		var aa;
	    		//loadMarkers(testJsonStr);
	    		//showMarker(testJsonStr);
	    	}
	    	
	    	var markergg = new BMap.Marker(bdPoint[0]);
	   	 	agmap.addOverlay(markergg); //添加GPS marker
	   	 	var labelgg = new BMap.Label("转换后的GPS坐标:"+str,{offset:new BMap.Size(20,-10)});
	   	 	markergg.setLabel(labelgg); //添加GPS label
	   	 	agmap.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	    }
	    
	    function addPoint(arr){
	    	
	    	var graphicsLayer=createGraphicsLayer();
	    	var iconUrl = 'http://192.168.31.6:8083/agMpiMap/resources/images/marker/location.png';
			var pictureMarkerSymbol = new esri.symbol.PictureMarkerSymbol(iconUrl,25,34);
			var bdPoint;
			var point;
			for (var i=0;i<arr.length;i++){
				bdPoint=arr[i][0];
				point = new esri.geometry.Point(bdPoint.lng,bdPoint.lat,agmap.spatialReference);
				var graphic = new esri.Graphic(point,pictureMarkerSymbol,null);
				graphicsLayer.add(graphic);
			}
			//agmap.centerAt(point);
	    }
	    
	</script>
</head>
<%--  <body onload="initData();mapSwitchCss();">--%>
<body onload="loadMap();">
	<%--  	<iframe src="" name="dghy_index_left"  id="dghy_index_left" frameborder="0" scrolling="no" width="100%" height="100%" ></iframe>--%>
	<!-- 右边地图 -->
	<div id="main">
		<div class="base-map">
			<div id="agmap" class="base-map-switch base-map-switch-center"
				onclick="showAgMap()">加载ag地图</div>
			<div id="agmap1" class="base-map-switch base-map-switch-center"
				onclick="showBdMap()">加载百度地图</div>
			<div id="vec" class="base-map-switch base-map-switch-active"
				onclick="showMap('vec')">地图</div>
			<div id="img" class="base-map-switch base-map-switch-center"
				onclick="showMap('img')">
				影像
				<div id="ano" class="base-map-ano">
					<input id="chkAno" type="checkbox" name="chkAno" value="chkAno"
						onchange="anoCtrl()" />标注
				</div>
			</div>
			<div id="vec" class="base-map-switch base-map-switch-active"
				onclick="queryTask()">查询</div>
			<%--	         <button data-dojo-type="dijit/form/Button" id="drawp">Point</button>--%>
			<button onclick="drawPoint()">Point</button>
		</div>
		<label id="zbDraowLabel" style="margin-left:0px;background:#cc00cc;"></label>
		<label id="zbLabel" style="margin-left:200px;"></label>
		<!-- 工具栏  -->
		<%--	  <%@ include file="/module/toolbar/toolbar.jsp"%>--%>
		<div class="zt" style="display:none;">
			<input id="proCombox" name="proCombox_n" /> <input id="themeCombox"
				name="themeCombox" />
		</div>
		<%--	  	<div class="map-switch" id="expand">--%>
		<%--	  		<li id="map_one" class="map-normal"></li>--%>
		<%--	  		<li id="map_two" class="map-earth" style="display: none;"></li>--%>
		<%--	  		<li id="map_tree" class="map-panorama" style="display: none;"></li>--%>
		<%--		</div>--%>
	</div>
	<!-- 多屏对比 -->
	<%--	<%@ include file="/module/multiscreen/multiscreen.jsp"%>--%>
</body>
</html>