Ext.onReady(function() {
    
			Ext.onReady(function(){
			    
				var submitBtn = new Ext.Button({
	            text : 'Confirm Pickup',
	            minWidth : 100,
	            handler: function() {
	            	console.log('inside submitBtn', this);
	            	console.log('grid', grid);
	            	var selection = grid.getSelectionModel().getSelection();
	            	console.log('selection', selection);
                    var orderIds='';
					var pickerName='';
					var pickupCode='';
                    for(var i = 0; i < selection.length ; i++){
                    	orderIds += selection[i].get('orderNumber')+",";
						pickerName = selection[i].get('picker');
						pickupCode = selection[i].get('pickUpConfirmationCode');
                    	
                    }
					
					Ext.Ajax.request({
					timeout : 120000,
					url: 'stome/confirmOrderPickup',
					 method: 'GET',
					params: {
						tcorderid: orderIds,
						pickername: pickerName,
						pickupcode : pickupCode
					
					},
					success: function (response)
					{
						
						var decodedResponse = Ext.decode( response.responseText );
						console.log('order Ids', decodedResponse);
					
					},
					failure : function(response)
					{
						var decodedResponse = Ext.decode( response.responseText );
						console.log('order Ids', decodedResponse);
					}
				});
                    console.log('order Ids', orderIds);
	            }});
				
				
			
			    // create the data store
			    var store = Ext.create('Ext.data.Store', {
			        extend: 'Ext.data.Store',
					fields: [ 
								{ name : 'orderNumber'},
								{ name : 'storeName', mapping: 'store.storeName'},
								{ name : 'address1',  mapping: 'address.address1'},
								{ name : 'city',  mapping: 'address.city'},
								{ name : 'state',  mapping: 'address.state'},
								{ name : 'postalCode',  mapping: 'address.postalCode'},
								{ name : 'contact',  mapping: 'address.contact'},
								{ name : 'weight'},
								{ name : 'stomeAmount'},
								{ name : 'status'},
								{ name : 'picker'},
								{ name : 'pickUpConfirmationCode'},
								{ name : 'deliveryConfirmationCOde'},
								
								
								],
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
			   

			    // create the Grid
			    var grid = Ext.create('Ext.grid.Panel', {
				selType: 'checkboxmodel',
				    buttons: [submitBtn],
				    buttonAlign : 'center',
			        store: store,
				       columns: {
						
			        	items: [
						{
			                text     : 'Order Number',
			                width     : 125,
			                dataIndex: 'orderNumber',
			                filter: true
			            },
			            {
			                text     : 'Store Name',
			                width    : 125,
			                dataIndex: 'storeName',
			                filter: true
			            },
			            {
			                text     : 'Address',
			                width    : 200,
			                dataIndex: 'address1',
			                filter: true
			            },
						{
			                text     : 'City',
			                width    : 90,
			                dataIndex: 'city',
			                filter: true
			            },
						{
			                text     : 'State',
			                width    : 50,
			                dataIndex: 'state',
			                filter: true
			            },
						
						{
			                text     : 'Postal Code',
			                width    : 95,
			                dataIndex: 'postalCode',
			                filter: true
			            },
						{
			                text     : 'Contact',
			                width    : 125,
			                dataIndex: 'contact',
			                filter: true
			            },
						{
			                text     : 'Weight (LBS)',
			                width    : 100,
			                dataIndex: 'weight',
			                filter: true
			            },
						{
			                text     : 'Stome $',
			                width    : 110,
			                dataIndex: 'stomeAmount',
			                filter: true
			            },
						{
			                text     : 'Status',
			                width    : 100,
			                dataIndex: 'status',
			                filter: true
			            },
						{
			                text     : 'Picker',
			                width    : 100,
			                dataIndex: 'picker',
			                filter: true
			            },
						{
			                text     : 'PickUp Confirmation#',
			                width    : 160,
			                dataIndex: 'pickUpConfirmationCode',
			                filter: true
			            },
						
						{
			                text     : 'Delivery Confirmation#',
			                width    : 160,
			                dataIndex: 'deliveryConfirmationCOde',
			                filter: true
			            }
						],
			        },
			        title: 'Order List',
			        viewConfig: {
			            stripeRows: true
			        }
			    });
				Ext.widget('viewport', {
					layout: 'fit',
					items: [grid]
				});
				
			});
});
