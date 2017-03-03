Ext.onReady(function(){
    Ext.BLANK_IMAGE_URL = 'js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    var sm = new Ext.grid.CheckboxSelectionModel();
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        sm,
        {header:'编号',dataIndex:'id',sortable:true},//设置编号排序
        {header:'名称',dataIndex:'name'},
        {header:'描述',dataIndex:'descn'},
        {header:'其他',dataIndex:'other'}
    ]);
   
    //proxy直接去读取josn数据
    var ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url:'madeJson.jsp'}),//提交的页面       
        reader: new Ext.data.JsonReader({
            totalProperty: 'totalProperty',
            root: 'root',
            successProperty :'success'
        }, [
            {name: 'id',mapping:'id',type:'int'},
            {name: 'name',mapping:'name',type:'string'},
            {name: 'descn',mapping:'descn',type:'string'},
            {name: 'other',mapping:'other',type:'string'}
        ])