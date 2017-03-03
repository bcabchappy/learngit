<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
<%
request.setCharacterEncoding("utf-8");
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path;
String agsupportUrl = "http://192.168.31.6:8083/dgsp";
String arcgisJsApiUrl = "http://192.168.15.83:8090/arcgis_js_v316";
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
	<link id="easyuiTheme" rel="stylesheet" type="text/css" href="<%=basePath%>/resources/easyui/themes/<%=themeName%>/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/css/<%=themeName%>/main.css"/>	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/resources/css/imgareaselect/imgareaselect-default.css" />
	
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=QDE1Bn3SWHhurwaPIdb1KG2Q"></script>
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
	<script src="<%=basePath%>/resources/js/imgareaselect/jquery.imgareaselect.pack.js"></script>
	<script src="<%=basePath%>/module/common/DataGridCellTip.js"></script>
	<script src="<%=basePath%>/module/common/customDatagrid.js"></script>
	<script src="<%=basePath%>/module/multiscreen/multiscreen.js"></script>
	<script src="./agMpiMap/bdMap/js/BDAnoLayer.js"></script>
	<script src="./agMpiMap/bdMap/js/BDImgLayer.js"></script>
	<script src="./agMpiMap/bdMap/js/BDVecLayer.js"></script>
	
	<style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
        }
        .base-map-ano{
            position: absolute;
            right: 0pt;
            top:18pt;
            background: #e6edf1;
            border: #96aed1 1px solid;
            padding: 4px 5px;
            padding-left: 0px;
            padding-top: 0px;
            display: none;
            font-weight: normal;
        }
        .base-map{
            position: absolute;
            right: 15pt;
            top:15pt;
            background: #f0f0f0;
            border: #96aed1 1px solid;
            width: auto;
            height: auto;
            z-index: 99;
            font:normal 11px "宋体",Arial;
            color:#868686;
        }
        .base-map-switch{
            padding: 4px 8px;
            float: left;
        }
        .base-map-switch-active{
            background:#e6edf1;
            font-weight: bold;
            color: #4d4d4d;
        }
        .base-map-switch:hover{
            cursor: pointer;
        }
        .base-map-switch-center{
            border: 1px #96aed1 solid;
            border-top:none;
            border-bottom:none;
        }
    </style>
    
	<script type="text/javascript">
        dojoConfig = {
            parseOnLoad: true,
             packages: [{
                 name: 'bdlib',
                 location: "http://192.168.15.83:8090/arcgis_js_v316/api/bdlib"
             }]
        };
    </script>
    <script>
        var vecMap;
        var imgMap;
        var anoMap;
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
        	 		agsMap = new Map("main", {
                         logo: false
                     });
                      vecMap = new BDVecLayer();
                      imgMap = new BDImgLayer();
                      anoMap = new BDAnoLayer();
                      agsMap.addLayer(vecMap);
                      agsMap.addLayers([imgMap,anoMap]);
                     imgMap.hide(),anoMap.hide();

                     var pt = new Point(7038512.810510807, 2629489.7975553474, new SpatialReference({ wkid: 102100 }));
                     agsMap.centerAndZoom(pt, 5);

//                     showMap = function(layer){
//                         设置按钮样式
//                         var baseMap = ["vec","img"];
//                         for(var i= 0, dl=baseMap.length;i<dl;i++){
//                             $("#"+baseMap[i]).removeClass("base-map-switch-active");
//                         }
//                         $("#"+layer).addClass("base-map-switch-active");
//                         设置显示地图
//                         switch(layer){
//                             case "img":{//影像
//                                 vecMap.hide();
//                                 imgMap.show();
//                                 $("#ano").show();
//                                 break;
//                             }
//                             default :{//地图
//                                 vecMap.show();
//                                 imgMap.hide(),anoMap.hide();
//                                 $("#ano").hide();
//                                 $("#chkAno").attr("checked",false);
//                                 break;
//                             }
//                         }
//                     };
//                     anoCtrl = function(){
//                         if($("#chkAno").attr("checked")){
//                             anoMap.show();
//                         }
//                         else{
//                             anoMap.hide();
//                         }
//                     }
                 });
        
        
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
		
		function loadMap() {
			return;
			if(!agsMap){
				agsMap = new esri.Map("main", {logo: false, slider: false}); 
			}
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
		    dojo.connect(agsMap, "onExtentChange", function(extent, delta, levelChange, lod){
		    	if(lod != undefined && lod != null){
<%--			    	for(var i = 0;i < multiscreen.multiscreenMaps.length; i++){--%>
<%--			    		var screenMap = multiscreen.multiscreenMaps[i].map;--%>
<%--			    		screenMap.centerAndZoom(extent.getCenter(), lod.level);--%>
<%--			    	}--%>
		    	}
			});
		    //document.getElementById("dghy_index_left").src = path + "/module/index/index_left.jsp";
<%--		    document.getElementById("dghy_index_left").onload = function(){--%>
<%--		    	mapToolBar = new MapToolBar(agsMap);--%>
<%--				mapToolBar.createNavigation();--%>
<%--		    }--%>

			//百度地图
<%--			var vecMap = new BDVecLayer();--%>
<%--			var imgMap = new BDImgLayer();--%>
<%--			var anoMap = new BDAnoLayer();--%>
<%--			agsMap.addLayer(vecMap);--%>
<%--			agsMap.addLayers([imgMap,anoMap]);--%>
			
			
		}
		
		function showAgMap(){
			 var ydtUrl="http://192.168.15.83:6080/arcgis/rest/services/CoreMap/xm_sghy_ydt/MapServer";
			 var jsxmUrl="http://192.168.15.83:6080/arcgis/rest/services/CoreMap/xm_sghy_jsxm/MapServer";
			 var ylydUrl="http://192.168.15.83:6080/arcgis/rest/services/ZxghMap/xm_zxgh_ylws/MapServer";
			 var layer = esri.layers.ArcGISTiledMapServiceLayer(ydtUrl);
			agsMap.addLayer(createTiledLayer(ydtUrl));
			agsMap.addLayer(createDynamicLayer(jsxmUrl));
			agsMap.addLayer(createDynamicLayer(ylydUrl));
	    }
	</script>
  </head>
<%--  <body onload="initData();mapSwitchCss();">--%>
  <body onload="loadMap();">
<%--  	<iframe src="" name="dghy_index_left"  id="dghy_index_left" frameborder="0" scrolling="no" width="100%" height="100%" ></iframe>--%>
	<!-- 右边地图 -->
	<div id="main">
	    <div class="base-map">
	        <div id="vec" class="base-map-switch base-map-switch-active" onclick="showMap('vec')">地图</div>
	        <div id="img" class="base-map-switch base-map-switch-center"  onclick="showMap('img')">影像
	            <div id="ano" class="base-map-ano">
	                <input id="chkAno" type="checkbox" name="chkAno" value="chkAno" onchange="anoCtrl()"/>标注
	            </div>
	        </div>
	        <div id="agmap" class="base-map-switch base-map-switch-center" onclick="showAgMap()">加载ag地图</div>
	    </div>		
	  <!-- 工具栏  -->
<%--	  <%@ include file="/module/toolbar/toolbar.jsp"%>--%>
	  <div class="zt"> 
	  	<input id="proCombox" name="proCombox_n"/> 
	  	<input id="themeCombox" name="themeCombox" /> 
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