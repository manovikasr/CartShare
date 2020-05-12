<html>
<head></head>

<body>
	<p>Hi ${pooler_name},</p>
	<p>Your <b>Order #${order_id}</b> is picked up by <b>${deliverer}</b> and will be delivered to you shortly.</p>
	<br/>
	<table border="1">
			<tr>
				<th>SKU</th>
				<th>Product Name</th>
				<th>Product Brand</th>
				<th>Product Description</th>
				<th>Product Price</th>
				<th>Quantity</th>
				<th>Total Price</th>
			</tr>
		
			
			<#list order.order_details as order_detail>
				<tr>
					<td><center>${order_detail.sku}</center></td>
					<td><center>${order_detail.product_name}</center></td>		
					<td><center>${order_detail.product_brand}</center></td>
					<td><center>${order_detail.product_desc}</center></td>
					<td><center>${order_detail.product_price}</center></td>
					<td><center>${order_detail.quantity} ${order_detail.unit_type}</center></td>
					<td><center>${order_detail.total_price}</center></td>
				</tr>
			</#list>
		</table>
		<br/>
	<p>Thanks,</p>
	<p>Cart Share Team</p>	
</body>
</html>