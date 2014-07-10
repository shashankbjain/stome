var Grid1Store = new Ext.data.Store({
      fields: [ 'orderNumber', 'storeName', 'address1','city','state','postalCode','contact', {name: 'weight', type: 'float'},{name: 'stomeAmount', type: 'float'} ],
      autoLoad: true,
	  proxy: {
						timeout : 120000,
						type: 'rest',
						url: 'stome/getOrderList',
						method: 'GET',
						headers :
						{
							'accept' : 'application/json'
						},
						  reader: {
							type: 'json',
							root: 'orders',	
							successProperty: 'success',
							totalProperty: 'totalCount'
						},
						listeners : {
						
						},
						simpleSortMode: true
					},
    });   

 
	var onReadyFunction = function(){
		
		 var submitBtn = new Ext.Button({
	            text : 'Submit',
	            minWidth : 100,
	            handler: function() {
	            	console.log('inside submitBtn', this);
	            	console.log('grid', grid);
	            	var selection = grid.getSelectionModel().getSelection();
	            	console.log('selection', selection);
                    var orderIds='';
                    for(var i = 0; i < selection.length ; i++){
                    	orderIds += selection[i].get('orderNumber')+",";
                    	//var gridrecord = grid.getSelectionModel().getSelected();
                        //name += (name===''?'':'')+selection[i].get('id');
                    }
                    console.log('order Ids', orderIds);
	            }
		 
			 	/*Ext.Ajax.request({
					url : 'http://www.google.com',
					params : orderids,
					method : httpMethod,
					success : function(response)
					{
						if(callback) callback(theStore, response, true, grid);
					},
					failure : function(response)
					{
						if(callback) callback(theStore, response, false, grid);
					}
				});*/
		 
	        });
		
		var columnSelectionModel = Ext.create('Ext.selection.CheckboxModel', {
		        columns: [{
		            xtype: 'checkcolumn',
		            text: 'Active',
		            dataIndex: 'orderNumber'
		   }]});
	
		var grid = new Ext.grid.GridPanel({
			  selModel : columnSelectionModel,
			  renderTo: Ext.getBody(),
	          frame: true,
	          title: 'Available Orders for Pickup',
	          width: 350,
	          //autoHeight: true,
	          buttons: [submitBtn],
	          buttonAlign : 'center',
	          store: Grid1Store,
	              columns: [
					  {
					    text: "", 
					    width: 20
					   },
	                  {
	                	  text: "OrderSummary", 
	                	 // locked   : true,
	                	  columnLines: false,
	                	  width: 250,
	                	  renderer : function(value, metaData, record, rowIndex, colIndex, store,grid){
	                		metaData.style = "white-space: pre-wrap;";
	                		var orderSummary="";
	              			console.log('id',record.data.orderNumber);
	              			console.log('weight',record.data.weight);
	              			console.log('stomeAmount',record.data.stomeAmount);
	                		if(record.data.storeName != null && record.data.storeName.length > 0){
	              				orderSummary = orderSummary+"Store:"+ record.data.storeName ;
	              			}
	              			if(record.data.orderNumber != null && record.data.orderNumber.length > 0){
	              				orderSummary = orderSummary+ " Order: "+record.data.orderNumber + " \n";
	              			}
	              			if(record.data.weight != null ){
	              				orderSummary = orderSummary+"<b> Weight:"+record.data.weight ;
	              			}
	              			if(record.data.stomeAmount != null ){
	              				orderSummary = orderSummary+ "  Fee: $"+record.data.stomeAmount + "</b> \n";
	              			}
	              			
	              			if(record.data.address1 != null && record.data.address1.length > 0){
	              				orderSummary = orderSummary+"Origin: "+record.data.address1 ;
	              			}
	              			if(record.data.postalCode != null && record.data.postalCode.length > 0){
	              				orderSummary = orderSummary+ "-<b>"+record.data.postalCode + "</b> \n";
	              			}
	              			
	              			console.log('orderSummary',orderSummary);
	                		return orderSummary;
	                	  }
	                  } //end of column orderSumamry
	              ]
	           });
      }


      Ext.onReady(onReadyFunction);