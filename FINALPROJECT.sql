create database ProjectFile

---------------------------------------------------------------TABLES----------------------------------------------------------------------
CREATE TABLE Users(
username varchar(20),
password varchar(20),
first_name varchar(20),
last_name varchar(20),
email varchar(50),
primary key(username)
)

CREATE TABLE User_mobile_numbers(
mobile_number varchar(20),
username varchar(20),
primary key(mobile_number,username),
foreign key(username) references Users ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE User_Addresses(
address varchar(100),
username varchar(20),
primary key(address,username),
foreign key(username) references Users ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE Customer(
username varchar(20),
points int,
primary key(username),
foreign key(username) references Users ON DELETE CASCADE ON UPDATE CASCADE
)


CREATE TABLE Admins(
username varchar(20),
primary key(username),
foreign key(username) references Users ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE Vendor(
username varchar(20),
activated bit,
company_name varchar(20),
bank_account_no varchar(20),
admin_username varchar(20),
primary key(username),
foreign key(username) references Users ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(admin_username) references Admins 
)

CREATE TABLE Delivery_Person(
username varchar(20),
is_activated bit,
primary key(username),
foreign key(username) references Users ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE Credit_Card(
number varchar(20),
expiry_date datetime NOT NULL,
cvv_code varchar(4),
primary key(number)
)

CREATE TABLE Delivery(
id  INT  IDENTITY,
type varchar(20),
time_duration int,
fees decimal(5,3),
username varchar(20),
primary key (id) ,
foreign key(username) references Admins ON DELETE CASCADE ON UPDATE CASCADE
)
create table GiftCard(
code varchar(10),
expiry_date datetime,
amount decimal(10,2),
username varchar(20),
primary key(code),
foreign key(username) references Admins ON DELETE CASCADE ON UPDATE CASCADE
)

CREATE TABLE Orders(
order_no int IDENTITY,
order_date date,
total_amount decimal (10,2),
cash_amount decimal (10,2),
credit_amount decimal(10,2),
payment_type varchar(20),
order_status varchar(20),
reamining_days int,
time_limit time,
customer_name varchar(20),
delivery_id int,
creditCard_number varchar(20),
Gift_Card_Code_Used varchar(10)
primary key(order_no),
foreign key(customer_name) references Customer ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(delivery_id) references Delivery,
foreign key(creditCard_number) references Credit_Card,
foreign key(Gift_Card_Code_Used) references GiftCard
)

Create table Product(
Serial_no INT IDENTITY ,
product_name varchar(20),
category varchar(20),
product_description varchar(20),
price decimal(10,2),
final_price decimal(10,2),
color varchar(20),
available bit,
rate int,
vendor_username  varchar(20),
customer_username varchar(20),
customer_order_id int
primary key (Serial_no),
foreign key(vendor_username) references Vendor ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(customer_order_id) References Orders ,
foreign key(customer_username) references Customer
)
create table CustomerAddstoCartProduct(
Serial_no INT,
customer_name varchar(20),
primary key( Serial_no , customer_name),
foreign key  (Serial_no) references Product ON DELETE CASCADE ON UPDATE CASCADE,
foreign key  (customer_name) references Customer
)
create table Todays_Deals(
deal_id INT IDENTITY,
deal_amount decimal,
expiry_date datetime,
admin_username varchar(20),
primary key(deal_id),
foreign key  (admin_username) references Admins ON DELETE CASCADE ON UPDATE CASCADE
)
create table Todays_Deals_Product(
deal_id INT,
serial_no INT,
primary key( deal_id, serial_no),
foreign key  (deal_id) references Todays_Deals ON DELETE CASCADE ON UPDATE CASCADE,
foreign key  (serial_no) references Product
)

create table offer(
offer_id INT IDENTITY,
offer_amount decimal,
expiry_date datetime,
primary key(offer_id)
)
create table offersOnProduct(
offer_id int,
serial_no int,
primary key(offer_id,serial_no),
foreign key  (offer_id) references offer ON DELETE CASCADE ON UPDATE CASCADE,
foreign key  (serial_no) references Product
)
create table Customer_Question_Product(
serial_no int,
customer_name varchar(20),
question varchar(20),
answer varchar(20),
primary key(customer_name,serial_no),
foreign key  (serial_no) references Product ON DELETE CASCADE ON UPDATE CASCADE,
foreign key  (customer_name) references Customer
)

create table Wishlist(
username varchar(20),
name varchar(20),
primary key(username,name),
foreign key(username) references Customer ON DELETE CASCADE ON UPDATE CASCADE
)
create table Wishlist_Product(
username varchar(20),
wish_name varchar(20),
serial_no int,

primary key(username,wish_name,serial_no),
foreign key(username,wish_name) references Wishlist ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(serial_no) references Product
)




CREATE TABLE Admin_Customer_Giftcard(
code varchar(10),
customer_name varchar(20),
admin_username varchar(20),
remaining_points int
primary key(code,customer_name,admin_username),
foreign key(code) references GiftCard ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(customer_name) references Customer,
foreign key(admin_username) references Admins
)

CREATE TABLE Admin_Delivery_Order(
delivery_username varchar(20),
order_no int, 
admin_username varchar(20),
delivery_window time,
primary key(delivery_username,order_no),
foreign key(delivery_username) references Delivery_Person ON DELETE CASCADE ON UPDATE CASCADE,
foreign key(order_no) references orders,
foreign key(admin_username) references Admins
)

create table Customer_CreditCard(
customer_name varchar(20),
cc_number varchar(20),
primary key(customer_name,cc_number),
foreign key(customer_name) references Customer ON DELETE CASCADE ON UPDATE CASCADE,
foreign key (cc_number) references Credit_Card
)

------------------------------------------------INSERTIONS-------------------------------------------------------------------------------------
INSERT INTO Users (username,first_name,last_name,[password],email)
VALUES ('hana.aly','hana','aly','pass1','hana.aly@guc.edu.eg')
INSERT INTO Users (username,first_name,last_name,[password],email)
VALUES ('ammar.yasser','ammar','yasser','pass4','ammar.yasser@guc.edu.eg')
INSERT INTO Users (username,first_name,last_name,[password],email)
VALUES ('nada.sharaf','nada','sharaf','pass7','nada.sharaf@guc.edu.eg')
INSERT INTO Users (username,first_name,last_name,[password],email)
VALUES ('hadeel.adel','hadeel','adel','pass13','hadeel.adel@guc.edu.eg')
INSERT INTO Users (username,first_name,last_name,[password],email)
VALUES ('mohamed.tamer','mohamed','tamer','pass16','mohamed.tamer@guc.edu.eg')

INSERT INTO ADMINS (username)
VALUES('hana.aly')
INSERT INTO ADMINS (username)
VALUES('nada.sharaf')

INSERT INTO Customer (username,points)
VALUES ('ammar.yasser',15)

INSERT INTO Vendor (username,activated,company_name,bank_account_no,admin_username)
VALUES('hadeel.adel',1,'Dello','47449349234','hana.aly')

INSERT INTO User_Addresses (username,address)
VALUES ('hana.aly','New Cairo')
INSERT INTO User_Addresses (username,address)
VALUES ('hana.aly','Heliopolis')

INSERT INTO User_mobile_numbers (username,mobile_number)
VALUES ('hana.aly','01111111111')
INSERT INTO User_mobile_numbers (username,mobile_number)
VALUES ('hana.aly','1211555411')

INSERT INTO Credit_Card (number,expiry_date,cvv_code)
VALUES ('4444-5555-6666-8888','10-19-2028','232')



--SET IDENTITY_INSERT Product ON;
INSERT INTO Product (product_name,category,product_description,
final_price,color,available,rate,vendor_username,price)
VALUES ('BAG','fashion','backbag',100,'yellow',1,0,'hadeel.adel',100)
--SET IDENTITY_INSERT Product Off;
--SET IDENTITY_INSERT Product ON;
INSERT INTO Product (product_name,category,product_description,
final_price,color,available,rate,vendor_username,price)
VALUES ('Blue pen','stationary','useful pen',10,'blue',1,0,'hadeel.adel',10)
--SET IDENTITY_INSERT Product off;
--SET IDENTITY_INSERT Product ON;
INSERT INTO Product (product_name,category,product_description,
final_price,color,available,rate,vendor_username,price)
VALUES ('Blue pen','stationary','useful pen',10,'blue',0,0,'hadeel.adel',10)
--SET IDENTITY_INSERT Product off;
--SET IDENTITY_INSERT Product ON;
--INSERT INTO Product (serial_no,product_name,category,product_description,
--final_price,color,available,rate,vendor_username,price)
--VALUES (4,'pencil','stationary','HBO.7',10,'red',0,0,'elam.mahmod',10)
--SET IDENTITY_INSERT Product off;



INSERT INTO CustomerAddstoCartProduct (serial_no,customer_name)
VALUES (1,'ammar.yasser')
INSERT INTO Delivery_Person (username, is_activated)
VALUES ('mohamed.tamer',1)

INSERT INTO Delivery (type,time_duration,fees)
VALUES ('pick-up',7,10)
INSERT INTO Delivery (type,time_duration,fees)
VALUES ('regular',14,30)
INSERT INTO Delivery (type,time_duration,fees)
VALUES ('speedy',1,50)

SET IDENTITY_INSERT Todays_Deals ON;
INSERT INTO Todays_Deals (deal_id,deal_amount,admin_username,expiry_date)
VALUES (1,30,'hana.aly','11-30-2019')
INSERT INTO Todays_Deals (deal_id,deal_amount,admin_username,expiry_date)
VALUES (2,40,'hana.aly','11-18-2019')
INSERT INTO Todays_Deals (deal_id,deal_amount,admin_username,expiry_date)
VALUES (3,50,'hana.aly','12-12-2019')
INSERT INTO Todays_Deals (deal_id,deal_amount,admin_username,expiry_date)
VALUES (4,10,'hana.aly','11-12-2019')
SET IDENTITY_INSERT Todays_Deals OFF

SET IDENTITY_INSERT offer ON
INSERT INTO offer (offer_id,offer_amount,expiry_date)
VALUES(1,50,'11-30-2019')
SET IDENTITY_INSERT offer OFF

INSERT INTO Wishlist(username,name)
VALUES ('ammar.yasser','fashion')


INSERT INTO Wishlist_Product (username,wish_name,serial_no)
VALUES ('ammar.yasser','fashion',2)

INSERT INTO Wishlist_Product (username,wish_name,serial_no)
VALUES ('ammar.yasser','fashion',4)

INSERT INTO Giftcard (code,expiry_date,amount)
VALUES ('G101','11-18-2019',100)

INSERT INTO Customer_CreditCard (customer_name,cc_number)
VALUES ('ammar.yasser','4444-5555-6666-8888')
-----------------------------------------------------Customer proc-------------------------------------------------------------------------------
GO
create proc customerRegister
@username varchar(20),
@first_name varchar(20),
@last_name varchar(20),
@password varchar(20),
@email varchar(50)
AS
INSERT into Users(username,first_name,last_name,password,email)
values(@username,@first_name,@last_name,@password,@email)
INSERT into Customer(username,points)
values(@username,0)

EXEC customerRegister 'ahmed.ashraf',  'ahmed',  'ashraf', 'pass123', 'ahmed@yahoo.com'

Go
create proc vendorRegister
@username varchar(20),
@first_name varchar(20),
@last_name varchar(20),
@password varchar(20),
@email varchar(50),
@company_name varchar(20),
@bank_account_no varchar(20)
AS
INSERT into Users(username,first_name,last_name,password,email)
values(@username,@first_name,@last_name,@password,@email)
INSERT into Vendor(username,company_name,bank_account_no,activated)
values(@username,@company_name,@bank_account_no,0)
EXEC vendorRegister 'eslam.mahmod',  'eslam',  'mahmod', 'pass1234', 'hopa@gmail.com', 'Market','132135213'

go
create proc userLogin
@username varchar(20),
@password varchar(20),
@success bit OUTPUT,
@type int OUTPUT
AS
select U.username , U.password
from Users U INNER JOIN
	Customer C on C.username=U.username INNER JOIN
	Vendor V on V.username=U.username INNER JOIN
	Admin A on A.username = U.username INNER JOIN
	Delivery_Person D on D.username = U.username
where U.username = @username and U.password = @password
if U.username is not null 
		set @success =1
		if C.username=U.username
		set @type =0
		else
		if V.username = U.username
		set @type =1
		else 
		if A.username = U.username
		set @type =2
		else 
		if D.username = U.username
		set @type =3
else
set @success = 0

EXEC userLogin 'eslam.mahmod','pass123'


GO
create proc addMobile
@username varchar(20),
@mobile_number varchar(20)
AS
INSERT INTO User_mobile_numbers(username,mobile_number)
 values (@username,@mobile_number)

 EXEC addMobile 'ahmed.ashraf' , '01000886089'

GO
create proc addAddress
@username varchar(20),
@address varchar(100)
AS
INSERT INTO User_Addresses(username,address)
 values (@username,@address)
  EXEC addAddress 'ahmed.ashraf' , 'nasr city'

GO
create proc showProducts
AS
SELECT* 
FROM product
EXEC showProducts

GO
create proc ShowProductsbyPrice
AS
SELECT* 
FROM product
ORDER BY price
EXEC ShowProductsbyPrice

GO
create proc searchbyname
@text varchar(20)
AS
SELECT*
FROM product
where product_name like '%'+ @text +'%'
EXEC searchbyname 'blue'

GO
create proc AddQuestion
@serial int,
@customer varchar(20),
@Question varchar(50)
AS
INSERT INTO Customer_Question_Product (serial_no,customer_name,Question)
values(@serial,@customer,@Question)
EXEC AddQuestion 1,'ahmed.ashraf', 'size?'


GO
create proc addToCart
@customername varchar(20),
@serial int
AS
INSERT INTO CustomerAddstoCartProduct(customer_name,Serial_no)
values(@customername,@serial)
EXEC addToCart 'ahmed.ashraf',1
EXEC addToCart 'ahmed.ashraf',2

SELECT *
FROM CustomerAddstoCartProduct

GO
create proc removefromCart
@customername varchar(20),
@serial int
AS
DELETE FROM CustomerAddstoCartProduct
WHERE Serial_no = @serial AND customer_name=@customername
EXEC removefromCart 'ahmed.ashraf' , 2


GO
create proc createWishlist
@customername varchar(20),
@name varchar(20)
AS
INSERT INTO Wishlist(username,name)
values(@customername,@name)

EXEC createWishlist 'ahmed.ashraf','fashion'



GO 
create proc AddtoWishlist
 @customername varchar(20),
 @wishlistname varchar(20),
 @serial int 
 AS

 INSERT INTO Wishlist_Product(username,wish_name,serial_no)
 values(@customername,@wishlistname,@serial)

 EXEC AddtoWishlist 'ahmad.ashraf','fashion',1
 EXEC AddtoWishlist 'ahmad.ashraf','fashion',2

 GO
 create proc removefromWishlist
 @customername varchar(20),
 @wishlistname varchar(20),
 @serial int 
 AS
DELETE FROM Wishlist_Product WHERE
@customername = username and @wishlistname=wish_name and @serial = serial_no
 
 EXEC removefromWishlist 'ahmad.ashraf','fashion',1

GO
create proc showWishlistProduct 
@customername varchar(20),
@name varchar(20) 
AS
SELECT * 
FROM Wishlist_Product
WHERE @customername = username and @wishlistname=name

EXEC showWishlistPro


GO
create proc viewMyCart
@customer varchar(20)
AS
SELECT* From CustomerAddstoCartProduct c inner join Product p on c.customer_name=p.customer_username
WHERE @customer = customer_name
EXEC viewMyCart 'ahmed.ashraf'

GO 
create proc calculatepriceOrder 
@customername varchar(20),
@sum decimal(10,2) output
AS 
set @sum = (SELECT SUM(final_price) FROM 
CustomerAddstoCartProduct x  inner join product p 
on p.serial_no = x.serial_no
where p.customer_username = @customername)
EXEC calculatepriceOrder 'ahmad.ashraf'

GO
create proc productsinorder
@customername varchar(20),
@orderID int
AS
SELECT c.*
FROM Product p inner join 
	CustomerAddstoCartProduct t 
	on p.Serial_no = t.Serial_no
	where @customername = t.customer_name

UPDATE Product 
set available = '0'
where Product.customer_order_id=@orderID

DELETE FROM CustomerAddstoCartProduct
	where(SELECT CustomerAddstoCartProduct.Serial_no
	FROM Product p inner join 
	CustomerAddstoCartProduct t on p.Serial_no = t.Serial_no
	WHERE @customername = t.customer_name
)=CustomerAddstoCartProduct.Serial_no






GO
create proc emptyCart 
@customername varchar(20)
AS 
DELETE FROM CustomerAddstoCartProduct where customer_name=@customername

GO 
create proc makeOrder
@customername varchar(20)
INSERT INTO Orders()
values(SELECT productname
FROM CustomerAddstoCartProduct where customer_name=@customername


GO
create proc cancelOrder
@orderid int
AS
DELETE FROM Orders WHERE (order_status='not processed' or order_status='in process') and order_no=@orderid

GO
create proc returnProduct 
@serialno int, 
@orderid int
AS
UPDATE Product
SET available = '1'
where Serial_no = @serialno AND customer_order_id=@orderid;

GO
create proc ShowproductsIbought 
@customername varchar(20)
AS
SELECT * 
From product p inner join Orders o
on p.customer_username = o.customer_name

GO
create proc rate
 @serialno int,
 @rate int ,
 @customername varchar(20) 
 AS
 UPDATE Product
 SET rate = @rate
 where customer_username=@customername AND Serial_no=@serialno

 GO
 CREATE PROC SpecifyAmount
  @customername varchar(20),
  @orderID int, 
  @cash decimal(10,2), 
  @credit decimal(10,2)
  AS
  SELECT t.total_amount , c.points
  FROM Orders o 
	  inner join Customer c
	  ON c.username = o.customer_name
	  inner join 
	  Customer_CreditCard cc on cc.customer_name=o.customer_name
  WHERE o.customer_name=@customername AND o.order_no=@orderID
  
   if (@cash + @credit) > t.total_amount
   update Customer
   set c.points=c.points+((@cash + @credit) - total_amount)
	else
	if (@cash + @credit) < t.total_amount
	update Customer
	set c.points=c.points-(total_amount-(@cash + @credit))



GO 
CREATE PROC AddCreditCard
 @creditcardnumber varchar(20), 
 @expirydate date , 
 @cvv varchar(4), 
 @customername varchar(20)
 AS
 INSERT into 
 Credit_Card(cvv_code,expiry_date,number)
 values(@cvv,@expirydate,@creditcardnumber)

 INSERT INTO Customer_CreditCard(cc_number,customer_name)
 values(@creditcardnumber,@customername)

 GO 
 CREATE PROC ChooseCreditCard
 @creditcard varchar(20), 
 @orderid int
 AS
 UPDATE Orders
 SET creditCard_number=@creditcard
 where order_no=@orderid

 GO
 CREATE PROC viewDeliveryTypes
 AS
 SELECT D.type
 FROM Delivery D

 GO
 CREATE PROC specifydeliverytype 
  @orderID int,
  @deliveryID int
  AS
  SELECT o.reamining_days,o.time_duration
  FROM Orders o inner join Delivery d on o.delivery_id=d.id
  WHERE o.order_no=@orderID
  if o.type='pick-up'
       Update Delivery
	   SET o.reamining_days = '0'
	  
 else
 if o.type='regular' --max 2 weeks
        Update Delivery
         SET r.reamining_days = 14-(d.time_duration)
else 
if
  o.type='speedy' --24 hrs
          Update Delivery
           SET r.reamining_days = 1-(d.time_duration)

-----------------------------------------------------Vendor proc--------------------------------------------------
GO
CREATE PROC postProduct
  @vendorUsername varchar(20), 
  @product_name varchar(20),
  @category varchar(20), 
  @product_description text ,
  @price decimal(10,2), 
  @color varchar(20)
  AS


INSERT INTO Product(vendor_username ,product_name,category,product_description,price,color)
 Values ( @vendorUsername, @product_name,@category,@product_description,@price,@color)
  
  EXEC postProduct 'eslam.mahmod', 'pencil','stationary', 
      'HB0.7' ,10, 'red'

	  
 GO
 Create Proc vendorviewProducts
@vendorname varchar(20)
AS
SELECT *
FROM Product
where vendor_username =@vendorname

EXEC vendorviewProducts  'eslam.mahmod'

GO
Create proc EditProduct
 @vendorname varchar(20),
 @serialnumber int,
 @product_name varchar(20) ,
 @category varchar(20),
@product_description text , 
@price decimal(10,2), 
@color varchar(20)
AS

 if @product_name is not null
update Product
set product_name=@product_name
where Serial_no = @serialnumber
if @category is not null
update Product
set category =@category
where Serial_no = @serialnumber
if  @product_description is not null
update Product
set product_description= @product_description
where Serial_no = @serialnumber
if  @price is not null
update Product
set price = @price
where Serial_no = @serialnumber
if @color is not null
update Product
set color =@color
where Serial_no = @serialnumber

EXEC EditProduct 'eslam.mahmod', '3',null,null,null,null, 'blue'
GO
create proc deleteProduct
@vendorname varchar(20),
@serialnumber int
AS
Delete from Product
where vendor_username = @vendorname AND Serial_no = @serialnumber

EXEC deleteProduct 'elam.mahmod' , 4

GO 
create proc viewQuestions
@vendorname varchar(20)
AS
SELECT *
FROM Customer_Question_Product
where @vendorname =vendor_username

EXEC viewQuestions 'hadeel.adel'

GO 
create proc answerQuestions
@vendorname varchar(20), 
@serialno int, 
@customername varchar(20), 
@answer text
AS
update Customer_Question_Product
set answer = @answer
where(SELECT vendor_username 
      from Customer_Question_Product c inner join Product p on  c.serial_no = p.Serial_no
      where p.customer_username= @customername)=@vendorname

	  EXEC answerQuestions 'hadeel.adel','1','ahmed.ashraf','40'

GO
create proc addOffer
@offeramount int, 
@expiry_date datetime
AS

update offer
set  offer_amount = @offeramount
update offer
set expiry_date = @expiry_date

EXEC addOffer '50','11/10/2019'

GO
create proc checkOfferonProduct
@serial int,
@activeoffer bit output
AS
if(SELECT serial_no
  FROM offersOnProduct) = @serial
set @activeoffer = 1
else
set @activeoffer = 0

EXEC checkOfferonProduct 

GO
create proc checkandremoveExpiredoffer
@offerid int 
AS
if(SELECT expiry_date
From offer) < CURRENT_TIMESTAMP

delete offerid FROM offer

EXEC checkandremoveExpiredoffer

GO
create proc applyOffer
@vendorname varchar(20),
@offerid int, 
@serial int
AS
if  exists   (select serial_no from offersOnProduct where serial_no= @serial )
    print  'already applied'
	ELSE insert  into offersOnProduct(offer_id,serial_no)
	Values (@offerid,@serial)

      DECLARE @val decimal
	 set @val= ( SELECT offer_amount
	        FROM offer where offer_id=@offerid)
	  declare @val2 decimal
	  set @val2= ( SELECT price from Product where serial_no = @serial and vendor_username = @vendorname )
	       update Product
     set final_price = price - @val2 where serial_no = @serial and vendor_username = @vendorname


----------------------------------ADMIN PROC-----------------------------------------------------------------------------
GO
CREATE PROC activateVendors
 @admin_username varchar(20),
 @vendor_username varchar(20)
 AS
 UPDATE Vendor
 SET Vendor.activated ='1' , Vendor.admin_username=@admin_username
 WHERE Vendor.username=@vendor_username

 exec activateVendors 'hania.aly' , 'eslam.mahmod'
 
drop proc inviteDeliveryPerson
ALTER TABLE Delivery_Person 
  ADD email VARCHAR(50)

 GO
 CREATE PROC inviteDeliveryPerson
  @delivery_username varchar(20),
  @delivery_email varchar(50)
  AS
  
  INSERT INTO Delivery_Person(username,email)
  values(@delivery_username,@delivery_email)

  exec inviteDeliveryPerson 'mohamed.tamer' ,  ' ​moha@gmail.com '

  GO
  CREATE PROC reviewOrders
  AS
  SELECT*
  FROM Orders
   
  exec reviewOrders

  GO
  CREATE PROC updateOrderStatusInProcess 
  @order_no int
  AS
  UPDATE Orders
  SET order_status='in process'
  WHERE order_no=@order_no

  exec updateOrderStatusInProcess  2

GO
CREATE PROC addDelivery 
@delivery_type varchar(20),
@time_duration int,
@fees decimal(5,3),
@admin_username varchar(20)
AS
INSERT INTO Delivery(type,time_duration,fees,username)
values(@delivery_type,@time_duration,@fees,@admin_username)

exec addDelivery 'pick-up' , 7 , 10 , 'hana.aly'

GO
CREATE PROC assignOrdertoDelivery
 @delivery_username varchar(20),
 @order_no int,
 @admin_username varchar(20)
 AS
INSERT INTO Admin_Delivery_Order(delivery_username,order_no,admin_username)
values(@delivery_username,@order_no,@admin_username)

exec assignOrdertoDelivery 'mohamed.tamer' , 2 , 'hana.aly'

GO
CREATE PROC createTodaysDeal
@deal_amount int,
@admin_username varchar(20),
@expiry_date datetime
AS
INSERT INTO Todays_Deals(deal_amount,admin_username,expiry_date)
values(@deal_amount,@admin_username,@expiry_date)
exec createTodaysDeal 30 , 'hana.aly' ,  '2019/11/30 '

GO
CREATE PROC checkTodaysDealOnProduct 
 @serial_no INT,
 @activeDeal BIT OUTPUT
 AS
 SELECT deal_amount, expiry_date
 FROM Todays_Deals_Product t inner join Todays_Deals td
 on t.deal_id=td.deal_id
 WHERE t.serial_no=@serial_no
 if deal_amount>0 and expiry_date>current_timestamp
 set @activeDeal='1'

 GO
 CREATE PROC addTodaysDealOnProduct
  @deal_id int, 
  @serial_no int
  AS
  INSERT INTO Todays_Deals_Product(deal_id,serial_no)
  values(@deal_id,@serial_no)
  exec  addTodaysDealOnProduct 1,2

GO
CREATE PROC removeExpiredDeal 
 @deal_iD int 
 AS
 DELETE FROM Todays_Deals
 WHERE expiry_date<current_timestamp
 exec removeExpiredDeal 1

GO
CREATE PROC  createGiftCard 
@code varchar(10),
@expiry_date datetime,
@amount int,
@admin_username varchar(20)
AS
INSERT INTO GiftCard(code,expiry_date,amount,username)
values(@code,@expiry_date,@amount,@admin_username)
exec createGiftCard 'G101' , '2019/12/30 ' , 100 , 'hana.aly'

GO
CREATE PROC removeExpiredGiftCard
@code varchar(10)
AS 
DELETE FROM GiftCard
WHERE code=@code and expiry_date<current_timestamp
exec removeExpiredGiftCard 'G101'

GO 
CREATE PROC checkGiftCardOnCustomer
@code varchar(10),
@activeGiftCard BIT OUTPUT
AS
SELECT x.customer_name
FROM Admin_Customer_Giftcard a inner join Customer c 
ON a.customer_name = c.username
WHERE a.code=@code
if x.customer_name is null
SET @activeGiftCard='0'
else
SET @activeGiftCard='1'


GO
CREATE PROC  giveGiftCardtoCustomer
 @code varchar(10),
 @customer_name varchar(20),
 @admin_username varchar(20)
 AS
 INSERT INTO Admin_Customer_Giftcard(code,customer_name,admin_username)
 values(@code,@customer_name,@admin_username)
 exec giveGiftCardtoCustomer 'G101' , 'ahmed.ashraf' , 'hana.aly'

 -----------------------------Delivery Proc-------------------------------------------
 
GO
create proc acceptAdminInvitation
@delivery_username varchar(20)
AS 
Update Delivery_Person
set is_activated = 0
where username = @delivery_username 


   exec acceptAdminInvitation ​'mohamed.tamer'



GO 
create proc deliveryPersonUpdateInfoh
@username varchar(20),
@first_name varchar(20),
@last_name varchar(20),
@password varchar(20),
@email varchar(50)
AS

ALTER TABLE Delivery_Person
ADD firstname VARCHAR(20);
ALTER TABLE Delivery_Person
ADD lastname VARCHAR(20);
ALTER TABLE Delivery_Person
ADD passwordd VARCHAR(20);
ALTER TABLE Delivery_Person
ADD email VARCHAR(50);
INSERT into Delivery_Person(username,firstname,lastname,passwordd,email)
values(@username,@first_name,@last_name,@password,@email)

exec  deliveryPersonUpdateInfoh 'mohamed.tamer' , 'mohamed' , 'tamer' , 'pass16' , '​mohamed.tamer@guc.edu.eg '

GO 
create proc viewmyorder
@deliveryperson varchar(20),
@_no int
AS
SELECT  *, username
FROM  Orders , Delivery_Person 
            
where  order_no =@_no and username = @deliveryperson

 exec  viewmyorder 'mohamed.tamer' , 5


GO
create proc specifyDeliveryWindow 
@delivery_username varchar(20),
@order_no int,
@delivery_window varchar(50)
AS

update Admin_Delivery_Order
set delivery_window = @delivery_window 
where delivery_username = @delivery_username and order_no = @order_no

exec  specifyDeliveryWindow  'mohamed.tamer' , 1, 'Today between 10 am and 3 pm'


GO 
create proc updateOrderStatusOutforDelivery
@order_no int
AS
update Orders
set order_status = 'out for delivery'
where order_no = @order_no

exec updateOrderStatusOutforDelivery 2
GO
create proc updateOrderStatusDelivered
@order_no int
AS
update Orders
set order_status = 'delivered'
where order_no = @order_no

exec updateOrderStatusDelivered 2

