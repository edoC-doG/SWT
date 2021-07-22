USE [master]
GO

CREATE DATABASE [PNJManagement]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'PNJManagement', FILENAME = N'Desktop\PNJManagement.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'PNJManagement_log', FILENAME = N'Desktop\PNJManagement_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO

ALTER DATABASE [PNJManagement] SET COMPATIBILITY_LEVEL = 130
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [PNJManagement].[dbo].[sp_fulltext_database] @action = 'enable'
end 
GO
ALTER DATABASE [PNJManagement] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [PNJManagement] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [PNJManagement] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [PNJManagement] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [PNJManagement] SET ARITHABORT OFF 
GO
ALTER DATABASE [PNJManagement] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [PNJManagement] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [PNJManagement] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [PNJManagement] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [PNJManagement] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [PNJManagement] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [PNJManagement] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [PNJManagement] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [PNJManagement] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [PNJManagement] SET  DISABLE_BROKER 
GO
ALTER DATABASE [PNJManagement]  SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [PNJManagement]  SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [PNJManagement]  SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [PNJManagement]  SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [PNJManagement]  SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [PNJManagement]  SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [PNJManagement]  SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [PNJManagement]  SET RECOVERY SIMPLE 
GO
ALTER DATABASE [PNJManagement]  SET  MULTI_USER 
GO
ALTER DATABASE [PNJManagement]  SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [PNJManagement]  SET DB_CHAINING OFF 
GO
ALTER DATABASE [PNJManagement]  SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [PNJManagement]  SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [PNJManagement] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [PNJManagement]  SET QUERY_STORE = OFF
GO
USE [PNJManagement] 
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
USE [PNJManagement] 
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Products](
	[productID] [nvarchar](50) NOT NULL,
	[nameProduct] [nvarchar](100) NULL,
	[image] [nvarchar](50) NULL,
	[description] [nvarchar](100) NULL,
	[price] [float] NULL,
	[categoryID] [int] NULL,
	[statusID] [int] NULL,
	[quantity] [int] NULL,
	[date] [date] NULL,
 CONSTRAINT [PK_Books1] PRIMARY KEY CLUSTERED 
(
	[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[categoryID] [int] NOT NULL,
	[categoryName] [nvarchar](50) NULL,
 CONSTRAINT [PK_Categories1] PRIMARY KEY CLUSTERED 
(
	[categoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[CodeDetail](
	[codeID] [int] NOT NULL,
	[userID] [nvarchar](50) NOT NULL,
	[statusID] [int] NULL,
 CONSTRAINT [PK_CodeDetail] PRIMARY KEY CLUSTERED 
(
	[codeID] ASC,
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Codes](
	[codeID] [int] NOT NULL,
	[codeValue] [int] NULL,
	[createDate] [date] NULL,
 CONSTRAINT [PK_Codes1] PRIMARY KEY CLUSTERED 
(
	[codeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Histories](
	[IDcart] [int] IDENTITY(1,1) NOT NULL,
	[totalPrice] [float] NULL,
	[dateOrder] [date] NULL,
	[dateShip] [date] NULL,
	[isPayment] [bit] NULL,
	[userID] [nvarchar](50) NULL,
	[codeID] [int] NULL,
 CONSTRAINT [PK_Cart1] PRIMARY KEY CLUSTERED 
(
	[IDcart] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[HistoryDetails](
	[IDcart] [int] NOT NULL,
	[quantity] [int] NULL,
	[productID] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_CartDetail] PRIMARY KEY CLUSTERED 
(
	[IDcart] ASC,
	[productID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[roleID] [int] NOT NULL,
	[roleName] [nvarchar](50) NULL,
 CONSTRAINT [PK_Roles] PRIMARY KEY CLUSTERED 
(
	[roleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Status](
	[statusID] [int] NOT NULL,
	[statusName] [nvarchar](20) NULL,
 CONSTRAINT [PK_Status1] PRIMARY KEY CLUSTERED 
(
	[statusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[userID] [nvarchar](50) NOT NULL,
	[username] [nvarchar](50) NULL,
	[password] [nvarchar](50) NULL,
	[roleID] [int] NULL,
	[statusID] [int] NULL,
 CONSTRAINT [PK_Users1_1] PRIMARY KEY CLUSTERED 
(
	[userID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Products] ([productID], [nameProduct], [image], [description], [price], [categoryID], [statusID], [quantity], [date]) VALUES (N'B001', N'abc', N'c', N'abc', 3, 1, 1, 1, CAST(N'2021-06-08' AS Date))
INSERT [dbo].[Products] ([productID], [nameProduct], [image], [description], [price], [categoryID], [statusID], [quantity], [date]) VALUES (N'B002', N'abc', N'c++', N'abc', 5.2, 1, 1, 70, CAST(N'2021-06-08' AS Date))
INSERT [dbo].[Products] ([productID], [nameProduct], [image], [description], [price], [categoryID], [statusID], [quantity], [date]) VALUES (N'B003', N'abc', N'timCook', N'abc', 30, 1, 1, 0, CAST(N'2021-06-08' AS Date))
INSERT [dbo].[Products] ([productID], [nameProduct], [image], [description], [price], [categoryID], [statusID], [quantity], [date]) VALUES (N'B004', N'abc', N'java', N'abc', 10, 1, 1, 7, CAST(N'2021-06-08' AS Date))
INSERT [dbo].[Products] ([productID], [nameProduct], [image], [description], [price], [categoryID], [statusID], [quantity], [date]) VALUES (N'B005', N'abc', N'cook', N'abc', 20, 1, 1, 2, CAST(N'2021-06-08' AS Date))
INSERT [dbo].[Products] ([productID], [nameProduct], [image], [description], [price], [categoryID], [statusID], [quantity], [date]) VALUES (N'B006', N'abc', N'cookForFun', N'abc', 50, 2, 2, 8, CAST(N'2021-06-08' AS Date))
GO
INSERT [dbo].[Categories] ([categoryID], [categoryName]) VALUES (1, N'Technology')
INSERT [dbo].[Categories] ([categoryID], [categoryName]) VALUES (2, N'Cook')
GO
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (1, N'U006', 1)
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (1, N'U007', 2)
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (2, N'U006', 1)
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (2, N'U007', 1)
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (4, N'U007', 1)
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (5, N'U006', 1)
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (5, N'U007', 1)
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (6, N'U006', 1)
INSERT [dbo].[CodeDetail] ([codeID], [userID], [statusID]) VALUES (6, N'U007', 1)
GO
INSERT [dbo].[Codes] ([codeID], [codeValue], [createDate]) VALUES (1, 10, CAST(N'2021-06-17' AS Date))
INSERT [dbo].[Codes] ([codeID], [codeValue], [createDate]) VALUES (2, 20, CAST(N'2021-06-17' AS Date))
INSERT [dbo].[Codes] ([codeID], [codeValue], [createDate]) VALUES (3, 30, CAST(N'2021-06-17' AS Date))
INSERT [dbo].[Codes] ([codeID], [codeValue], [createDate]) VALUES (4, 40, CAST(N'2021-06-19' AS Date))
INSERT [dbo].[Codes] ([codeID], [codeValue], [createDate]) VALUES (5, 50, CAST(N'2021-06-19' AS Date))
INSERT [dbo].[Codes] ([codeID], [codeValue], [createDate]) VALUES (6, 60, CAST(N'2021-06-19' AS Date))
GO
SET IDENTITY_INSERT [dbo].[Histories] ON 

INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (248, 13.199999809265137, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-24' AS Date), 1, N'U004', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (249, 30, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-26' AS Date), 1, N'U004', 5)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (250, 24, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U004', 2)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (251, 9.1800003051757812, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U004', 1)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (252, 18, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-24' AS Date), 1, N'U004', 4)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (253, 2.7000000476837158, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-22' AS Date), 1, N'U003', 1)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (254, 82.6199951171875, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-26' AS Date), 1, N'U001', 1)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (255, 3, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U001', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (256, 3, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-23' AS Date), 1, N'U001', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (257, 36.180000305175781, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U001', 1)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (258, 10.199999809265137, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-23' AS Date), 1, N'U001', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (259, 30, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U001', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (260, 18.360000610351562, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-23' AS Date), 0, N'U001', 1)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (261, 20.100000381469727, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-24' AS Date), 0, N'U006', 5)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (262, 40.200000762939453, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-24' AS Date), 1, N'U001', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (263, 8, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-24' AS Date), 1, N'U001', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (264, 49.680000305175781, CAST(N'2021-06-22' AS Date), CAST(N'2021-06-23' AS Date), 1, N'U006', 1)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (265, 4.679999828338623, CAST(N'2021-06-23' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U007', 1)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (266, 35.200000762939453, CAST(N'2021-06-24' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U004', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (267, 35.200000762939453, CAST(N'2021-06-24' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U004', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (268, 5.1999998092651367, CAST(N'2021-06-24' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U004', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (269, 5.1999998092651367, CAST(N'2021-06-24' AS Date), CAST(N'2021-06-25' AS Date), 1, N'U004', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (270, 10.399999618530273, CAST(N'2021-06-24' AS Date), CAST(N'2021-06-26' AS Date), 1, N'U004', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (271, 25.200000762939453, CAST(N'2021-06-28' AS Date), CAST(N'2021-06-30' AS Date), 1, N'khanhkk', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (272, 20, CAST(N'2021-06-28' AS Date), CAST(N'2021-06-30' AS Date), 1, N'khanhkk', NULL)
INSERT [dbo].[Histories] ([IDcart], [totalPrice], [dateOrder], [dateShip], [isPayment], [userID], [codeID]) VALUES (273, 10, CAST(N'2021-06-28' AS Date), CAST(N'2021-06-30' AS Date), 1, N'khanhkk', NULL)
SET IDENTITY_INSERT [dbo].[Histories] OFF
GO
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (248, 1, N'B001')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (248, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (249, 2, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (250, 1, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (251, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (252, 1, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (253, 1, N'B001')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (254, 9, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (255, 1, N'B001')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (256, 1, N'B001')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (257, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (257, 1, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (258, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (259, 1, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (260, 2, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (261, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (261, 1, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (262, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (262, 1, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity],[productID]) VALUES (263, 2, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (264, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (264, 1, N'B006')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (265, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (266, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (266, 1, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (267, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (267, 1, N'B003')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (268, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (269, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (270, 2, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (271, 1, N'B002')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (271, 2, N'B004')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (272, 1, N'B005')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (272, 1, N'B006')
INSERT [dbo].[HistoryDetails] ([IDcart], [quantity], [productID]) VALUES (273, 1, N'B004')
GO
INSERT [dbo].[Roles] ([roleID], [roleName]) VALUES (1, N'User')
INSERT [dbo].[Roles] ([roleID], [roleName]) VALUES (2, N'Admin')
GO
INSERT [dbo].[Status] ([statusID], [statusName]) VALUES (1, N'Active')
INSERT [dbo].[Status] ([statusID], [statusName]) VALUES (2, N'InActive')
GO
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'khanhkk', N'khanhabc', N'1234', 1, 1)
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'khanhkt', N'khanhdaica', N'5562', 2, 1)
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'U001', N'gia bao', N'123', 1, 1)
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'U002', N'sang', N'123', 2, 1)
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'U003', N'hieu', N'123', 1, 1)
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'U004', N'loc', N'123', 1, 1)
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'U005', N'tu', N'123', 1, 1)
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'U006', N'khoa', N'123', 1, 1)
INSERT [dbo].[Users] ([userID], [username], [password], [roleID], [statusID]) VALUES (N'U007', N'gau', N'123', 1, 1)
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_Products_Categories] FOREIGN KEY([categoryID])
REFERENCES [dbo].[Categories] ([categoryID])
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_Products_Categories]
GO
ALTER TABLE [dbo].[Products]  WITH CHECK ADD  CONSTRAINT [FK_Products_Status] FOREIGN KEY([statusID])
REFERENCES [dbo].[Status] ([statusID])
GO
ALTER TABLE [dbo].[Products] CHECK CONSTRAINT [FK_Products_Status]
GO
ALTER TABLE [dbo].[CodeDetail]  WITH CHECK ADD  CONSTRAINT [FK_CodeDetail_Codes] FOREIGN KEY([codeID])
REFERENCES [dbo].[Codes] ([codeID])
GO
ALTER TABLE [dbo].[CodeDetail] CHECK CONSTRAINT [FK_CodeDetail_Codes]
GO
ALTER TABLE [dbo].[CodeDetail]  WITH CHECK ADD  CONSTRAINT [FK_CodeDetail_Status] FOREIGN KEY([statusID])
REFERENCES [dbo].[Status] ([statusID])
GO
ALTER TABLE [dbo].[CodeDetail] CHECK CONSTRAINT [FK_CodeDetail_Status]
GO
ALTER TABLE [dbo].[CodeDetail]  WITH CHECK ADD  CONSTRAINT [FK_CodeDetail_Users] FOREIGN KEY([userID])
REFERENCES [dbo].[Users] ([userID])
GO
ALTER TABLE [dbo].[CodeDetail] CHECK CONSTRAINT [FK_CodeDetail_Users]
GO
ALTER TABLE [dbo].[Histories]  WITH CHECK ADD  CONSTRAINT [FK_Histories_Codes] FOREIGN KEY([codeID])
REFERENCES [dbo].[Codes] ([codeID])
GO
ALTER TABLE [dbo].[Histories] CHECK CONSTRAINT [FK_Histories_Codes]
GO
ALTER TABLE [dbo].[Histories]  WITH CHECK ADD  CONSTRAINT [FK_Histories_Users] FOREIGN KEY([userID])
REFERENCES [dbo].[Users] ([userID])
GO
ALTER TABLE [dbo].[Histories] CHECK CONSTRAINT [FK_Histories_Users]
GO
ALTER TABLE [dbo].[HistoryDetails]  WITH CHECK ADD  CONSTRAINT [FK_CartDetail_Products] FOREIGN KEY([productID])
REFERENCES [dbo].[Products] ([productID])
GO
ALTER TABLE [dbo].[HistoryDetails] CHECK CONSTRAINT [FK_CartDetail_Books]
GO
ALTER TABLE [dbo].[HistoryDetails]  WITH CHECK ADD  CONSTRAINT [FK_CartDetail_Cart] FOREIGN KEY([IDcart])
REFERENCES [dbo].[Histories] ([IDcart])
GO
ALTER TABLE [dbo].[HistoryDetails] CHECK CONSTRAINT [FK_CartDetail_Cart]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Roles] FOREIGN KEY([roleID])
REFERENCES [dbo].[Roles] ([roleID])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Roles]
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Status] FOREIGN KEY([statusID])
REFERENCES [dbo].[Status] ([statusID])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Status]
GO
USE [master]
GO
