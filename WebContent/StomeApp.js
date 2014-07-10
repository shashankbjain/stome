Ext.application({
    name: 'stome',
    appFolder: 'stome',
	controllers: [
        'stomeController'
    ],
	
    launch: function() 
	{
        Ext.create('Ext.container.Viewport', {
        layout: {
            type: 'border',
            padding: 5
        },		
		items : 
			[ 
				{
					region : 'center',
					layout : 'column',
					width: '50%', height:'50%',
					split : true,
					header : false,
					collapsible: true,
					items : [ 
								{xtype: 'userlist', id:'orderlist', width: '100%', height:'50%',selType: 'checkboxmodel',collapsible : true,selModel: {mode: 'MULTI'}} 
						]
				},
			]
        });
	}
});



	