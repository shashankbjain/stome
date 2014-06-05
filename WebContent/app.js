Ext.onReady(function() {
    
			Ext.onReady(function(){
			    
			    /**
			     * Custom function used for column renderer
			     * @param {Object} val
			     */
			    function change(val) {
			        if (val > 0) {
			            return '<span style="color:green;">' + val + '</span>';
			        } else if (val < 0) {
			            return '<span style="color:red;">' + val + '</span>';
			        }
			        return val;
			    }

			    /**
			     * Custom function used for column renderer
			     * @param {Object} val
			     */
			    function pctChange(val) {
			        if (val > 0) {
			            return '<span style="color:green;">' + val + '%</span>';
			        } else if (val < 0) {
			            return '<span style="color:red;">' + val + '%</span>';
			        }
			        return val;
			    }

				var model = Ext.create('Ext.data.Model', {
					fields: ['orderNumber','storeName', 'address1','address2','city', 'state','postalCode','weight', 'picker','stomeAmount'],
					
				});
				
				
			
			    // create the data store
			    var store = Ext.create('Ext.data.Store', {
			        extend: 'Ext.data.Store',
					model: model,
					autoLoad: true,
					proxy: {
						timeout : 120000,
						type: 'rest',
						url: '/stome/getOrderList',
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
						
			        	items: [{
			        		text	  : '#',
			        		widht	  : 50,
							dataIndex : 'id',
			        		filter	  : {
			        			type: 'int',
			        			minValue: 1
							}
						},
						{
			                text     : 'Company',
			                flex     : 1,
			                dataIndex: 'orderNumber',
			                filter: true
			            },
			            {
			                text     : 'Price',
			                width    : 300,
			                renderer : 'usMoney',
			                dataIndex: 'storeName',
			                filter: true
			            },
			            {
			                text     : 'Change',
			                width    : 300,
			                renderer : change,
			                dataIndex: 'address1',
			                filter: true
			            },
			            {
			                text     : '% Change',
			                width    : 75,
			                renderer : pctChange,
			                dataIndex: 'pctChange',
			                filter: true
			            },
			            {
			                xtype	 : 'datecolumn',
							text     : 'Last Updated',
			                width    : 85,
			                format 	 : 'm/d/Y',
			                dataIndex: 'lastChange',
			                filter: true
			            },
			            {
			                text     : 'Category',
			                flex     : 0.5,
			                dataIndex: 'category',
			                filter: 'combo'
			            },
			            {
			                text     : 'Country',
			                flex     : 0.5,
			                dataIndex: 'country',
			                filter: 'list'
			            },
			            {
			                xtype: 'actioncolumnpro',
			                items: [{
			                    iconCls: 'edit',
			                    tooltip: 'Edit this'
			                },{
			                	iconCls: 'delete',
			                	tooltip: 'Delete this',
			                	hideIndex: 'not_delete'
							},{
			                    clsFn: function(record, item) {
			                    	if (Math.random() > 0.3) {
			                    		item.tooltip = 'Deactivate this';
			                    		return 'inactivate';
			                    	} else {
			                    		item.tooltip = 'Activate this';
			                    		return 'activate';
									}
								}
			                }]
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
				grid.down('actioncolumnpro').on('actionclick', function(grid, store, record, action) {
					alert('Action: ' + action + ' on record: ' + record.getId());
				});
			});
});
