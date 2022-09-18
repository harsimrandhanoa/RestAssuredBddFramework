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


@Order
Feature: Managing orders
  I want to add,modify and delete orders from mongo db using restful node api
  
Background: Logged In
  Given I look for valid session or else login into api using email 'abc@testnew.com' and password '123'
  And I set relevant end point for 'basket'
  
  @AddOrder
  Scenario: Adding a new order to basket collection
  Given Product '<ProductName>' priced '<ProductPrice>' of Quantity '<ProductQuantity>' is already added to database
  Given I want to add a new 'order'
  And I send a post request to basket for  basket '<BasketName>' and quantity '<ProductQuantity>'

 
 Then I get valid response code for 'add' orders
 And I get a valid response as well for 'add' orders
 And The order named '<BasketName>' is saved in mongodb 
  
    Examples: 
   | ProductName  | ProductPrice    | ProductQuantity  |BasketName       |BasketQuantity  |
   | Keyboard1     | 10             | 10                |ComputerBasket1 | 10             |
   
  
   @GetOrder
  Scenario: Gettting a order from basket collection
  Given Order '<BasketName>' for Quantity '<BasketQuantity>' with '<ProductName>' ,'<ProductQuantity>' ,'<ProductPrice>' is already added to database
  Given I set relevant end point for 'getting' orders for '<BasketName>'
  And I send a get request for orders
  Then I get valid response code for 'get' orders
  Then I get valid response for get orders for '<ProductName>' and '<BasketName>' and '<BasketQuantity>'
  
   Examples: 
   | ProductName  | ProductPrice    | ProductQuantity  |BasketName     |BasketQuantity  |
   | Keyboard2    | 20              | 20              |ComputerBasket2 | 20             |
   
  
  @DeleteOrder
  Scenario: Deleting a order from basket collection
  Given Order '<BasketName>' for Quantity '<BasketQuantity>' with '<ProductName>' ,'<ProductQuantity>' ,'<ProductPrice>' is already added to database
  Given I set relevant end point for 'delete' orders for '<BasketName>'
  And I send a delete request for orders
  Then I get valid response code for 'delete' orders
  And I get a valid response as well for 'delete' orders
  And The order named '<BasketName>' is deleted from mongodb 
  
   Examples: 
   | ProductName  | ProductPrice    | ProductQuantity  |BasketName     |BasketQuantity  |
   | Keyboard3    | 30              | 30              |ComputerBasket3 | 30             |