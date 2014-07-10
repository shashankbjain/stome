Ext.onReady(function() {
    
			Ext.onReady(function(){
			    
			
			
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
								{ name : 'cotact',  mapping: 'address.contact'},
								{ name : 'weight'},
								{ name : 'stomeAmount'},
								{ name : 'status'},
								{ name : 'picker'},
								{ name : 'deliveryAddress'},
								
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
			        store: store,
				       columns: {
						
			        	items: [
						{
			                text     : 'Order Number',
			                flex     : 1,
			                dataIndex: 'orderNumber',
			                filter: true
			            },
			            {
			                text     : 'Store Name',
			                width    : 300,
			                dataIndex: 'storeName',
			                filter: true
			            },
			            {
			                text     : 'Address',
			                width    : 300,
			                dataIndex: 'address1',
			                filter: true
			            },
						{
			                text     : 'City',
			                width    : 300,
			                dataIndex: 'city',
			                filter: true
			            },
						{
			                text     : 'State',
			                width    : 300,
			                dataIndex: 'state',
			                filter: true
			            },
						
						{
			                text     : 'Postal Code',
			                width    : 300,
			                dataIndex: 'postalCode',
			                filter: true
			            },
						{
			                text     : 'Contact',
			                width    : 300,
			                dataIndex: 'contact',
			                filter: true
			            },
						{
			                text     : 'Weight',
			                width    : 300,
			                dataIndex: 'weight',
			                filter: true
			            },
						{
			                text     : 'Stome Amount',
			                width    : 300,
			                dataIndex: 'stomeAmount',
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
