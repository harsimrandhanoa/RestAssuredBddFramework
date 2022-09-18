#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template


@Product
Feature: Managing products
  I want to add,modify and delete products from mongo db using restful node api
  
Background: Logged In
  Given I look for valid session or else login into api using email 'abc@testnew.com' and password '123'
  And I set relevant end point for 'products'
  
  @AddProduct
  Scenario: Adding a new product to products collection
  Given I want to add a new 'product'
  And I send a post request
  | ProductName    | Quantity     | Price           | 
  | <ProductName>  | <Quantity>   | <Price>         | 
 
  Then I get valid response code for 'add' product
  And I get a valid response as well for 'add' product
  And The product is saved in mongodb with name '<ProductName>' quantity '<Quantity>' and price '<Price>'
  
    Examples: 
   | ProductName       | Quantity  | Price |
   | Table1           |   12      | 12    |
   
   
  @UpdateProduct
  Scenario: Updating a available product from products collection
  Given I set relevant end point for 'update' products for '<ProductName>'
  And We create Product '<ProductName>' priced '<Price>' of Quantity '<Quantity>' in db to update or delete it
  And '<ProductName>' is available in db to be modified
  And I send a update request for '<ProductName>'  to change price to '<NewPrice>' and quantity to '<NewQuantity>'
  Then I get valid response code for 'update' product
  And I get a valid response as well for 'update' product
  And The product is saved in mongodb with name '<ProductName>' quantity '<NewQuantity>' and price '<NewPrice>'

 Examples: 
   | ProductName     |Price  |Quantity  | NewPrice       | NewQuantity |
   | Table2        |20       |   20     |10                |  12         |
  

  @DeleteProduct
  Scenario: Deleting a available product from products collection
  Given I set relevant end point for 'delete' products for '<ProductName>'
  And We create Product '<ProductName>' priced '<Price>' of Quantity '<Quantity>' in db to update or delete it
  And '<ProductName>' is available in db to be modified
  And I send a delete request for products
  Then I get valid response code for 'delete' product
  And I get a valid response as well for 'delete' product
  And The product '<ProductName>' is deleted from mongodb
  
   Examples: 
   | ProductName       | Quantity | Price |
   | Table3           |   10     | 12    |
   
   

  
  



