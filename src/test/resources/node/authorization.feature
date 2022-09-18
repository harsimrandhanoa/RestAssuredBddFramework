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

@Login
Feature: Managing Sessions
  I want to test login functionality to node app using both valid and invalid scenarios
  
  @ValidLogin
  Scenario: Login using valid credentials
  Given I login into app using '<ValidEmail>' and '<Password>'
  And I get correct response code for 'valid' login
  And I have the authorisation token available
  
  Examples: 
  |  ValidEmail     |Password    |
  |  abc@testnew.com|    123     |
  
  @InvalidLogin
  Scenario: Login using invalid credentials
  Given I login into app using '<InvalidEmail>' and '<Password>'
  And I get correct response code for 'invalid' login
    
  Examples: 
  |  InvalidEmail         |Password    |
  |  badeemail@testnew.com|    123     |
  
    
    
  
        
  
 
 