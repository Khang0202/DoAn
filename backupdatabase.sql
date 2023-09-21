USE [master]
GO
/****** Object:  Database [mapwarning]    Script Date: 21/09/2023 2:03:11 CH ******/
CREATE DATABASE [mapwarning]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'mapwarning', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\mapwarning.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 10%)
 LOG ON 
( NAME = N'mapwarning_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.MSSQLSERVER\MSSQL\DATA\mapwarning_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [mapwarning] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [mapwarning].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [mapwarning] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [mapwarning] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [mapwarning] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [mapwarning] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [mapwarning] SET ARITHABORT OFF 
GO
ALTER DATABASE [mapwarning] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [mapwarning] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [mapwarning] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [mapwarning] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [mapwarning] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [mapwarning] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [mapwarning] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [mapwarning] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [mapwarning] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [mapwarning] SET  DISABLE_BROKER 
GO
ALTER DATABASE [mapwarning] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [mapwarning] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [mapwarning] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [mapwarning] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [mapwarning] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [mapwarning] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [mapwarning] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [mapwarning] SET RECOVERY FULL 
GO
ALTER DATABASE [mapwarning] SET  MULTI_USER 
GO
ALTER DATABASE [mapwarning] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [mapwarning] SET DB_CHAINING OFF 
GO
ALTER DATABASE [mapwarning] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [mapwarning] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [mapwarning] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [mapwarning] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'mapwarning', N'ON'
GO
ALTER DATABASE [mapwarning] SET QUERY_STORE = ON
GO
ALTER DATABASE [mapwarning] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [mapwarning]
GO
/****** Object:  Table [dbo].[Address]    Script Date: 21/09/2023 2:03:11 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Address](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idprovince] [int] NOT NULL,
	[iddistrict] [int] NOT NULL,
	[town] [nvarchar](50) NOT NULL,
	[info] [nvarchar](100) NULL,
	[idcoordinates] [int] NULL,
 CONSTRAINT [PK_Address] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Coordinates]    Script Date: 21/09/2023 2:03:11 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Coordinates](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[latitude] [decimal](18, 5) NOT NULL,
	[longtitute] [decimal](18, 5) NOT NULL,
 CONSTRAINT [PK_Coordinates] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[District]    Script Date: 21/09/2023 2:03:11 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[District](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[idprovince] [int] NOT NULL,
	[district] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_District] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Province]    Script Date: 21/09/2023 2:03:11 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Province](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[province] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Province] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 21/09/2023 2:03:11 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](100) NOT NULL,
	[password] [nvarchar](100) NOT NULL,
	[firstname] [nvarchar](50) NULL,
	[lastname] [nvarchar](50) NULL,
	[birthday] [date] NULL,
	[email] [nvarchar](100) NULL,
	[phone] [numeric](18, 0) NULL,
	[sex] [nvarchar](10) NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Warning]    Script Date: 21/09/2023 2:03:11 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Warning](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[iduser] [int] NOT NULL,
	[idaddress] [int] NOT NULL,
	[info] [nvarchar](max) NULL,
	[createdtime] [datetime] NOT NULL,
 CONSTRAINT [PK_Warning] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[District] ON 

INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (1, 1, N'An Phú    ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (2, 1, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (3, 1, N'Châu Phú  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (4, 1, N'Chợ Mới   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (5, 1, N'Long Xuyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (6, 1, N'Phú Tân   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (7, 1, N'Tân Châu  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (8, 1, N'Thoại Sơn ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (9, 1, N'Tri Tôn   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (10, 2, N'Bà Rịa    ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (11, 2, N'Châu Đức  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (12, 2, N'Côn Đảo   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (13, 2, N'Đất Đỏ    ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (14, 2, N'Long Điền ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (15, 2, N'Tân Thành ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (16, 2, N'Vũng Tàu  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (17, 3, N'Bắc Giang ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (18, 3, N'Hiệp Hòa  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (19, 3, N'Lạng Giang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (20, 3, N'Lục Nam   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (21, 3, N'Lục Ngạn  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (22, 3, N'Sơn Động  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (23, 3, N'Tân Yên   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (24, 3, N'Việt Yên  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (25, 3, N'Yên Dũng  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (26, 3, N'Yên Thế   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (27, 4, N'Ba Bể     ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (28, 4, N'Bạch Thông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (29, 4, N'Chợ Đồn   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (30, 4, N'Chợ Mới   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (31, 4, N'Na Rì     ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (32, 4, N'Ngân Sơn  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (33, 4, N'Pác Nặm   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (34, 5, N'Đông Hải  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (35, 5, N'Giá Rai   ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (36, 5, N'Hòa Bình  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (37, 5, N'Hồng Dân  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (38, 5, N'Phước Long')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (39, 5, N'U Minh    ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (40, 5, N'Vĩnh Lợi  ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (41, 6, N'Gia Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (42, 6, N'Lương Tài')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (43, 6, N'Quế Võ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (44, 6, N'Tiên Du')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (45, 6, N'Yên Phong')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (46, 6, N'Thành phố Bắc Ninh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (47, 7, N'Ba Tri')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (48, 7, N'Bình Đại')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (49, 7, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (50, 7, N'Chợ Lách')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (51, 7, N'Giồng Trôm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (52, 7, N'Mỏ Cày Bắc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (53, 7, N'Mỏ Cày Nam')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (54, 7, N'Thạnh Phú')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (55, 7, N'Thành phố Bến Tre')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (56, 8, N'An Lão')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (57, 8, N'An Nhơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (58, 8, N'Hoài Ân')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (59, 8, N'Hoài Nhơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (60, 8, N'Phù Cát')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (61, 8, N'Phù Mỹ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (62, 8, N'Quy Nhơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (63, 8, N'Tây Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (64, 8, N'Tây Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (65, 8, N'Vân Canh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (66, 9, N'Bàu Bàng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (67, 9, N'Bến Cát')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (68, 9, N'Dầu Tiếng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (69, 9, N'Dĩ An')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (70, 9, N'Phú Giáo')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (71, 9, N'Thủ Dầu Một')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (72, 9, N'Tân Uyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (73, 9, N'Uông Bí')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (74, 10, N'Bình Long')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (75, 10, N'Bù Đăng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (76, 10, N'Bù Đốp')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (77, 10, N'Bù Gia Mập')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (78, 10, N'Chơn Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (79, 10, N'Dong Xoai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (80, 10, N'Hớn Quản')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (81, 10, N'Lộc Ninh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (82, 10, N'Phú Riềng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (83, 11, N'Bắc Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (84, 11, N'Hàm Thuận Bắc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (85, 11, N'Hàm Thuận Nam')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (86, 11, N'Phan Thiết')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (87, 11, N'Tánh Linh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (88, 11, N'Tuy Phong')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (89, 12, N'Cà Mau')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (90, 12, N'Cái Nước')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (91, 12, N'Dam Doi')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (92, 12, N'Năm Căn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (93, 12, N'Ngọc Hiển')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (94, 12, N'Phú Tân')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (95, 12, N'Thới Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (96, 12, N'Trần Văn Thời')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (97, 12, N'U Minh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (98, 13, N'Bình Thủy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (99, 13, N'Cái Răng')
GO
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (100, 13, N'Cờ Đỏ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (101, 13, N'Ninh Kiều')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (102, 13, N'Ô Môn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (103, 13, N'Thốt Nốt')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (104, 14, N'Bảo Lâm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (105, 14, N'Bảo Lạc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (106, 14, N'Hạ Lang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (107, 14, N'Hà Quảng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (108, 14, N'Hoà An')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (109, 14, N'Nguyên Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (110, 14, N'Phục Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (111, 14, N'Quảng Uyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (112, 14, N'Thạch An')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (113, 14, N'Thông Nông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (114, 14, N'Trà Lĩnh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (115, 15, N'Cẩm Lệ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (116, 15, N'Hải Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (117, 15, N'Hòa Vang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (118, 15, N'Liên Chiểu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (119, 15, N'Ngũ Hành Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (120, 15, N'Sơn Trà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (121, 15, N'Thanh Khê')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (122, 16, N'Buôn Đôn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (123, 16, N'Buôn Hồ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (124, 16, N'Buôn Ma Thuột (Thành phố)')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (125, 16, N'Cư Kuin')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (126, 16, N'Ea H''leo')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (127, 16, N'Ea Kar')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (128, 16, N'Ea Súp')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (129, 16, N'Krông Ana')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (130, 16, N'Krông Búk')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (131, 16, N'Krông Năng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (132, 17, N'Cư Jút')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (133, 17, N'Đăk Glong')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (134, 17, N'Đắk Mil')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (135, 17, N'Đắk R''Lấp')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (136, 17, N'Krông Nô')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (137, 17, N'Tuy Đức')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (138, 18, N'Diên Biên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (139, 18, N'Diên Biên Đông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (140, 18, N'Diên Biên Phủ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (141, 18, N'Mường Ảng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (142, 18, N'Mường Chà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (143, 18, N'Mường Lay')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (144, 18, N'Mường Nhé')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (145, 18, N'Nậm Pồ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (146, 19, N'Biên Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (147, 19, N'Cẩm Mỹ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (148, 19, N'Định Quán')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (149, 19, N'Long Khánh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (150, 19, N'Long Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (151, 19, N'Nhơn Trạch')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (152, 19, N'Tân Phú')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (153, 19, N'Thống Nhất')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (154, 19, N'Trảng Bom')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (155, 19, N'Vĩnh Cửu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (156, 19, N'Xuân Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (157, 20, N'Cao Lãnh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (158, 20, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (159, 20, N'Hồng Ngự')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (160, 20, N'Lai Vung')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (161, 20, N'Lấp Vò')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (162, 20, N'Tam Nông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (163, 20, N'Tân Hồng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (164, 20, N'Tân Phú')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (165, 20, N'Thanh Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (166, 20, N'Tràm Chim')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (167, 21, N'Ayun Pa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (168, 21, N'Chư Păh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (169, 21, N'Chư Prông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (170, 21, N'Chư Sê')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (171, 21, N'Đăk Đoa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (172, 21, N'Đăk Pơ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (173, 21, N'Đức Cơ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (174, 21, N'Ia Grai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (175, 21, N'Ia Pa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (176, 21, N'KBang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (177, 21, N'Kông Chro')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (178, 21, N'Krông Pa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (179, 22, N'Bắc Mê')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (180, 22, N'Bắc Quang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (181, 22, N'Dong Van')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (182, 22, N'Hà Giang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (183, 22, N'Hoàng Su Phì')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (184, 22, N'Quản Bạ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (185, 22, N'Quang Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (186, 22, N'Vị Xuyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (187, 22, N'Xín Mần')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (188, 22, N'Yên Minh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (189, 23, N'Bình Lục')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (190, 23, N'Duy Tiên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (191, 23, N'Kim Bảng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (192, 23, N'Lý Nhân')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (193, 23, N'Phủ Lý')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (194, 23, N'Thanh Liêm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (195, 24, N'Ba Đình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (196, 24, N'Cầu Giấy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (197, 24, N'Đống Đa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (198, 24, N'Hà Đông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (199, 24, N'Hoàn Kiếm')
GO
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (200, 24, N'Hoàng Mai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (201, 24, N'Long Biên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (202, 24, N'Nam Từ Liêm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (203, 24, N'Tây Hồ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (204, 24, N'Thanh Trì')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (205, 24, N'Gia Lâm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (206, 24, N'Đan Phượng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (207, 24, N'Hoài Đức')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (208, 24, N'Mỹ Đức')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (209, 24, N'Phú Xuyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (210, 24, N'Phúc Thọ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (211, 24, N'Quốc Oai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (212, 24, N'Sơn Tây')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (213, 24, N'Ứng Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (214, 24, N'Thạch Thất')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (215, 24, N'Thanh Oai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (216, 24, N'Ba Vì')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (217, 24, N'Mê Linh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (218, 25, N'Cẩm Xuyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (219, 25, N'Can Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (220, 25, N'Hà Tĩnh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (221, 25, N'Hương Khê')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (222, 25, N'Hương Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (223, 25, N'Kỳ Anh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (224, 25, N'Lộc Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (225, 25, N'Thạch Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (226, 25, N'Vũ Quang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (227, 26, N'Bình Giang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (228, 26, N'Cẩm Giàng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (229, 26, N'Chí Linh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (230, 26, N'Gia Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (231, 26, N'Hải Dương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (232, 26, N'Kim Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (233, 26, N'Kinh Môn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (234, 26, N'Nam Sách')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (235, 26, N'Ninh Giang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (236, 26, N'Thanh Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (237, 26, N'Thanh Miện')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (238, 26, N'Tứ Kỳ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (239, 27, N'An Dương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (240, 27, N'An Lão')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (241, 27, N'Biên Giang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (242, 27, N'Đồ Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (243, 27, N'Dương Kinh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (244, 27, N'Hải An')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (245, 27, N'Hồng Bàng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (246, 27, N'Kiến An')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (247, 27, N'Kiến Thụy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (248, 27, N'Lê Chân')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (249, 27, N'Ngô Quyền')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (250, 27, N'Thủy Nguyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (251, 27, N'Tiên Lãng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (252, 27, N'Vĩnh Bảo')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (253, 28, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (254, 28, N'Châu Thành A')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (255, 28, N'Châu Thành B')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (256, 28, N'Long Mỹ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (257, 28, N'Ngã Bảy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (258, 28, N'Phụng Hiệp')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (259, 28, N'Vị Thanh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (260, 29, N'Cao Phong')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (261, 29, N'Đà Bắc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (262, 29, N'Hòa Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (263, 29, N'Kim Bôi')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (264, 29, N'Lạc Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (265, 29, N'Lạc Thủy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (266, 29, N'Luông Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (267, 29, N'Kỳ Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (268, 29, N'Mai Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (269, 29, N'Tân Lạc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (270, 29, N'Yên Thủy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (271, 30, N'Ân Thi')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (272, 30, N'Khoái Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (273, 30, N'Mỹ Hào')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (274, 30, N'Phù Cừ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (275, 30, N'Tiên Lữ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (276, 30, N'Văn Giang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (277, 30, N'Văn Lâm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (278, 30, N'Văn Lễ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (279, 30, N'Yên Mỹ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (280, 31, N'Cẩm Lệ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (281, 31, N'Diên Khánh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (282, 31, N'Khánh Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (283, 31, N'Khánh Vĩnh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (284, 31, N'Nha Trang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (285, 31, N'Ninh Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (286, 31, N'Vạn Ninh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (287, 32, N'An Biên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (288, 32, N'An Minh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (289, 32, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (290, 32, N'Giang Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (291, 32, N'Hà Tiên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (292, 32, N'Hòn Đất')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (293, 32, N'Kiên Hải')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (294, 32, N'Kiên Lương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (295, 32, N'Phú Quốc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (296, 32, N'Rạch Giá')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (297, 32, N'Tân Hiệp')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (298, 32, N'U Minh Thượng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (299, 33, N'Dak Glei')
GO
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (300, 33, N'Đắk Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (301, 33, N'Đắk Tô')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (302, 33, N'Ia H''Drai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (303, 33, N'Kon Plông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (304, 33, N'Kon Rẫy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (305, 33, N'Kon Tum')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (306, 33, N'Ngọc Hồi')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (307, 33, N'Sa Thầy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (308, 33, N'Tu Mơ Rông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (309, 34, N'Mường Tè')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (310, 34, N'Phong Thổ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (311, 34, N'Sìn Hồ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (312, 34, N'Tam Đường')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (313, 34, N'Tan Uyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (314, 34, N'Lai Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (315, 34, N'Mường Ảng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (316, 34, N'Nậm Nhùn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (317, 35, N'Bảo Lâm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (318, 35, N'Bảo Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (319, 35, N'Cát Tiên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (320, 35, N'Da Huoai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (321, 35, N'Di Linh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (322, 35, N'Dức Trọng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (323, 35, N'Đạ Huoai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (324, 35, N'Đạ Tẻh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (325, 35, N'Lâm Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (326, 35, N'Lạc Dương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (327, 35, N'Lâm Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (328, 35, N'Di Linh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (329, 36, N'Bắc Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (330, 36, N'Bình Gia')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (331, 36, N'Cao Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (332, 36, N'Hữu Lũng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (333, 36, N'Lạng Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (334, 36, N'Lộc Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (335, 36, N'Thái Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (336, 36, N'Văn Lãng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (337, 37, N'Bắc Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (338, 37, N'Bảo Thắng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (339, 37, N'Bảo Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (340, 37, N'Bát Xát')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (341, 37, N'Muộn Thịnh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (342, 37, N'Sa Pa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (343, 37, N'Si Ma Cai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (344, 37, N'Văn Bàn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (345, 38, N'Ben Lức')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (346, 38, N'Cần Đước')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (347, 38, N'Cần Giuộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (348, 38, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (349, 38, N'Đức Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (350, 38, N'Đức Huệ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (351, 38, N'Kiến Tường')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (352, 38, N'Mộc Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (353, 38, N'Tân An')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (354, 38, N'Tân Hưng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (355, 38, N'Tân Thạnh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (356, 38, N'Tân Trụ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (357, 38, N'Trảng Bàng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (358, 39, N'Giao Thủy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (359, 39, N'Hải Hậu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (360, 39, N'Mỹ Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (361, 39, N'Nam Trực')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (362, 39, N'Nam Định')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (363, 39, N'Nghĩa Hưng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (364, 39, N'Xuân Trường')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (365, 39, N'Trực Ninh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (366, 39, N'Vụ Bản')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (367, 40, N'Anh Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (368, 40, N'Con Cuông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (369, 40, N'Cửa Lò')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (370, 40, N'Diễn Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (371, 40, N'Đô Lương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (372, 40, N'Hưng Nguyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (373, 40, N'Kỳ Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (374, 40, N'Nam Đàn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (375, 40, N'Nghi Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (376, 40, N'Nghĩa Đàn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (377, 40, N'Quế Phong')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (378, 40, N'Quỳ Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (379, 40, N'Quỳ Hợp')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (380, 40, N'Quỳnh Lưu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (381, 40, N'Thái Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (382, 40, N'Tương Dương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (383, 40, N'Yên Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (384, 40, N'Vinh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (385, 41, N'Gia Viễn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (386, 41, N'Hoa Lư')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (387, 41, N'Kim Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (388, 41, N'Nho Quan')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (389, 41, N'Ninh Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (390, 41, N'Tam Điệp')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (391, 41, N'Yên Khánh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (392, 42, N'Bác Ái')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (393, 42, N'Ninh Hải')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (394, 42, N'Ninh Phước')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (395, 42, N'Ninh Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (396, 42, N'Phan Rang - Tháp Chàm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (397, 42, N'Thuận Bắc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (398, 42, N'Thuận Nam')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (399, 43, N'Cẩm Khê')
GO
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (400, 43, N'Đoan Hùng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (401, 43, N'Hạ Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (402, 43, N'Lâm Thao')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (403, 43, N'Phú Thọ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (404, 43, N'Tam Nông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (405, 43, N'Tân Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (406, 43, N'Thanh Ba')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (407, 43, N'Yên Lập')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (408, 43, N'Phù Ninh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (409, 44, N'Đông Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (410, 44, N'Đồng Xuân')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (411, 44, N'Phú Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (412, 44, N'Sông Cầu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (413, 44, N'Sông Hinh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (414, 44, N'Tây Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (415, 44, N'Tuy An')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (416, 44, N'Tuy Hòa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (417, 45, N'Bố Trạch')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (418, 45, N'Đồng Hới')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (419, 45, N'Lệ Thủy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (420, 45, N'Min Đức')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (421, 45, N'Quảng Ninh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (422, 45, N'Quảng Trạch')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (423, 45, N'Tuyên Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (424, 46, N'Bắc Trà My')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (425, 46, N'Đại Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (426, 46, N'Điện Bàn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (427, 46, N'Duy Xuyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (428, 46, N'Hội An')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (429, 46, N'Nam Giang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (430, 46, N'Nông Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (431, 46, N'Núi Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (432, 46, N'Phú Ninh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (433, 46, N'Quế Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (434, 46, N'Tây Giang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (435, 46, N'Thăng Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (436, 46, N'Tiên Phước')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (437, 47, N'Ba Tơ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (438, 47, N'Bình Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (439, 47, N'Duc Pho')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (440, 47, N'Lý Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (441, 47, N'Min Đức')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (442, 47, N'Nghĩa Hành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (443, 47, N'Quảng Ngãi (Thành phố)')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (444, 47, N'Sơn Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (445, 47, N'Sơn Tây')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (446, 47, N'Tư Nghĩa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (447, 47, N'Trà Bồng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (448, 47, N'Trà Bồng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (449, 48, N'Ba Chẽ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (450, 48, N'Bình Liêu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (451, 48, N'Cô Tô')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (452, 48, N'Đầm Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (453, 48, N'Dông Triều')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (454, 48, N'Hải Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (455, 48, N'Hòn Đất')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (456, 48, N'Quảng Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (457, 48, N'Uông Bí')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (458, 48, N'Vân Đồn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (459, 48, N'Yên Hưng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (460, 49, N'Cam Lộ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (461, 49, N'Cam Lộ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (462, 49, N'Con Cưng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (463, 49, N'Đa Krông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (464, 49, N'Gio Linh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (465, 49, N'Hải Lăng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (466, 49, N'Hướng Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (467, 49, N'Quảng Trị')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (468, 49, N'Triệu Phong')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (469, 49, N'Vĩnh Linh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (470, 50, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (471, 50, N'Cu Lao Dung')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (472, 50, N'Kế Sách')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (473, 50, N'Long Phú')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (474, 50, N'Mỹ Tú')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (475, 50, N'Mỹ Xuyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (476, 50, N'Ngã Năm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (477, 50, N'Sóc Trăng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (478, 50, N'Thạnh Trị')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (479, 50, N'Trần Đề')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (480, 50, N'Vĩnh Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (481, 51, N'Bắc Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (482, 51, N'Mai Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (483, 51, N'Mộc Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (484, 51, N'Mường La')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (485, 51, N'Phù Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (486, 51, N'Quỳnh Nhai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (487, 51, N'Sơn La')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (488, 51, N'Sông Mã')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (489, 51, N'Thường Xuân')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (490, 51, N'Yên Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (491, 52, N'Bến Cầu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (492, 52, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (493, 52, N'Dương Minh Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (494, 52, N'Gò Dầu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (495, 52, N'Hòa Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (496, 52, N'Tân Biên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (497, 52, N'Tân Châu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (498, 52, N'Trảng Bàng')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (499, 53, N'Đông Hưng')
GO
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (500, 53, N'Hưng Hà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (501, 53, N'Kiến Xương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (502, 53, N'Quỳnh Phụ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (503, 53, N'Thái Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (504, 53, N'Thái Thụy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (505, 53, N'Tiền Hải')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (506, 53, N'Vũ Thư')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (507, 54, N'Dại Từ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (508, 54, N'Định Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (509, 54, N'Đồng Hỷ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (510, 54, N'Phổ Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (511, 54, N'Sông Công')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (512, 54, N'Thái Nguyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (513, 54, N'Võ Nhai')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (514, 55, N'Bá Thước')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (515, 55, N'Cẩm Thủy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (516, 55, N'Đông Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (517, 55, N'Hà Trung')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (518, 55, N'Hậu Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (519, 55, N'Hoằng Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (520, 55, N'Lang Chánh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (521, 55, N'Ngọc Lặc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (522, 55, N'Nga Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (523, 55, N'Như Xuân')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (524, 55, N'Nông Cống')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (525, 55, N'Quan Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (526, 55, N'Quan Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (527, 55, N'Quảng Xương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (528, 55, N'Thạch Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (529, 55, N'Thanh Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (530, 55, N'Thiệu Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (531, 55, N'Tĩnh Gia')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (532, 55, N'Vĩnh Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (533, 55, N'Yên Định')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (534, 56, N'A Lưới')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (535, 56, N'Hương Thủy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (536, 56, N'Hương Trà')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (537, 56, N'Nam Đông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (538, 56, N'Phong Điền')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (539, 56, N'Phú Lộc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (540, 56, N'Phú Vang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (541, 56, N'Quảng Điền')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (542, 56, N'Thị xã Hương Thủy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (543, 56, N'Thành phố Huế')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (544, 57, N'Cai Lậy')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (545, 57, N'Cái Bè')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (546, 57, N'Cù Lao Dung')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (547, 57, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (548, 57, N'Chợ Gạo')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (549, 57, N'Gò Công')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (550, 57, N'Gò Công Đông')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (551, 57, N'Gò Công Tây')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (552, 57, N'Mỹ Tho')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (553, 57, N'Tân Phước')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (554, 57, N'Vĩnh Long')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (555, 58, N'Càng Long')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (556, 58, N'Châu Thành')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (557, 58, N'Cầu Kè')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (558, 58, N'Duyên Hải')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (559, 58, N'Tiểu Cần')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (560, 58, N'Trà Cú')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (561, 58, N'Trà Vinh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (562, 58, N'Long Điền')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (563, 59, N'Chiêm Hóa')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (564, 59, N'Hàm Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (565, 59, N'Na Hang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (566, 59, N'Sơn Dương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (567, 59, N'Tuyên Quang')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (568, 59, N'Yên Sơn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (569, 60, N'Bình Minh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (570, 60, N'Long Hồ')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (571, 60, N'Mang Thít')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (572, 60, N'Tam Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (573, 60, N'Trà Ôn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (574, 60, N'Vũng Liêm')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (575, 60, N'Vĩnh Long')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (576, 61, N'Bình Xuyên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (577, 61, N'Lập Thạch')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (578, 61, N'Phúc Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (579, 61, N'Sông Lô')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (580, 61, N'Tam Đảo')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (581, 61, N'Tam Dương')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (582, 61, N'Vĩnh Tường')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (583, 61, N'Yên Lạc')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (584, 62, N'Lục Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (585, 62, N'Mù Căng Chải')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (586, 62, N'Trạm Tấu')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (587, 62, N'Văn Chấn')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (588, 62, N'Văn Yên')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (589, 62, N'Yên Bái')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (590, 62, N'Yên Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (591, 63, N'Quận 1')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (592, 63, N'Quận 2')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (593, 63, N'Quận 3')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (594, 63, N'Quận 4')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (595, 63, N'Quận 5')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (596, 63, N'Quận 6')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (597, 63, N'Quận 7')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (598, 63, N'Quận 8')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (599, 63, N'Quận 9')
GO
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (600, 63, N'Quận 10')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (601, 63, N'Quận 11')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (602, 63, N'Quận 12')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (603, 63, N'Bình Tân')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (604, 63, N'Bình Thạnh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (605, 63, N'Gò Vấp')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (606, 63, N'Phú Nhuận')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (607, 63, N'Tân Bình')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (608, 63, N'Tân Phú')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (609, 63, N'Thủ Đức')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (610, 63, N'Bình Chánh')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (611, 63, N'Nhà Bè')
INSERT [dbo].[District] ([id], [idprovince], [district]) VALUES (612, 63, N'Cần Giờ')
SET IDENTITY_INSERT [dbo].[District] OFF
GO
SET IDENTITY_INSERT [dbo].[Province] ON 

INSERT [dbo].[Province] ([id], [province]) VALUES (1, N'An Giang')
INSERT [dbo].[Province] ([id], [province]) VALUES (2, N'Bà Rịa - Vũng Tàu')
INSERT [dbo].[Province] ([id], [province]) VALUES (3, N'Bắc Giang')
INSERT [dbo].[Province] ([id], [province]) VALUES (4, N'Bắc Kạn')
INSERT [dbo].[Province] ([id], [province]) VALUES (5, N'Bạc Liêu')
INSERT [dbo].[Province] ([id], [province]) VALUES (6, N'Bắc Ninh')
INSERT [dbo].[Province] ([id], [province]) VALUES (7, N'Bến Tre')
INSERT [dbo].[Province] ([id], [province]) VALUES (8, N'Bình Định')
INSERT [dbo].[Province] ([id], [province]) VALUES (9, N'Bình Dương')
INSERT [dbo].[Province] ([id], [province]) VALUES (10, N'Bình Phước')
INSERT [dbo].[Province] ([id], [province]) VALUES (11, N'Bình Thuận')
INSERT [dbo].[Province] ([id], [province]) VALUES (12, N'Cà Mau')
INSERT [dbo].[Province] ([id], [province]) VALUES (13, N'Cần Thơ')
INSERT [dbo].[Province] ([id], [province]) VALUES (14, N'Cao Bằng')
INSERT [dbo].[Province] ([id], [province]) VALUES (15, N'Đà Nẵng')
INSERT [dbo].[Province] ([id], [province]) VALUES (16, N'Đắk Lắk')
INSERT [dbo].[Province] ([id], [province]) VALUES (17, N'Đắk Nông')
INSERT [dbo].[Province] ([id], [province]) VALUES (18, N'Điện Biên')
INSERT [dbo].[Province] ([id], [province]) VALUES (19, N'Đồng Nai')
INSERT [dbo].[Province] ([id], [province]) VALUES (20, N'Đồng Tháp')
INSERT [dbo].[Province] ([id], [province]) VALUES (21, N'Gia Lai')
INSERT [dbo].[Province] ([id], [province]) VALUES (22, N'Hà Giang')
INSERT [dbo].[Province] ([id], [province]) VALUES (23, N'Hà Nam')
INSERT [dbo].[Province] ([id], [province]) VALUES (24, N'Hà Nội')
INSERT [dbo].[Province] ([id], [province]) VALUES (25, N'Hà Tĩnh')
INSERT [dbo].[Province] ([id], [province]) VALUES (26, N'Hải Dương')
INSERT [dbo].[Province] ([id], [province]) VALUES (27, N'Hải Phòng')
INSERT [dbo].[Province] ([id], [province]) VALUES (28, N'Hậu Giang')
INSERT [dbo].[Province] ([id], [province]) VALUES (29, N'Hòa Bình')
INSERT [dbo].[Province] ([id], [province]) VALUES (30, N'Hưng Yên')
INSERT [dbo].[Province] ([id], [province]) VALUES (31, N'Khánh Hòa')
INSERT [dbo].[Province] ([id], [province]) VALUES (32, N'Kiên Giang')
INSERT [dbo].[Province] ([id], [province]) VALUES (33, N'Kon Tum')
INSERT [dbo].[Province] ([id], [province]) VALUES (34, N'Lai Châu')
INSERT [dbo].[Province] ([id], [province]) VALUES (35, N'Lâm Đồng')
INSERT [dbo].[Province] ([id], [province]) VALUES (36, N'Lạng Sơn')
INSERT [dbo].[Province] ([id], [province]) VALUES (37, N'Lào Cai')
INSERT [dbo].[Province] ([id], [province]) VALUES (38, N'Long An')
INSERT [dbo].[Province] ([id], [province]) VALUES (39, N'Nam Định')
INSERT [dbo].[Province] ([id], [province]) VALUES (40, N'Nghệ An')
INSERT [dbo].[Province] ([id], [province]) VALUES (41, N'Ninh Bình')
INSERT [dbo].[Province] ([id], [province]) VALUES (42, N'Ninh Thuận')
INSERT [dbo].[Province] ([id], [province]) VALUES (43, N'Phú Thọ')
INSERT [dbo].[Province] ([id], [province]) VALUES (44, N'Phú Yên')
INSERT [dbo].[Province] ([id], [province]) VALUES (45, N'Quảng Bình')
INSERT [dbo].[Province] ([id], [province]) VALUES (46, N'Quảng Nam')
INSERT [dbo].[Province] ([id], [province]) VALUES (47, N'Quảng Ngãi')
INSERT [dbo].[Province] ([id], [province]) VALUES (48, N'Quảng Ninh')
INSERT [dbo].[Province] ([id], [province]) VALUES (49, N'Quảng Trị')
INSERT [dbo].[Province] ([id], [province]) VALUES (50, N'Sóc Trăng')
INSERT [dbo].[Province] ([id], [province]) VALUES (51, N'Sơn La')
INSERT [dbo].[Province] ([id], [province]) VALUES (52, N'Tây Ninh')
INSERT [dbo].[Province] ([id], [province]) VALUES (53, N'Thái Bình')
INSERT [dbo].[Province] ([id], [province]) VALUES (54, N'Thái Nguyên')
INSERT [dbo].[Province] ([id], [province]) VALUES (55, N'Thanh Hóa')
INSERT [dbo].[Province] ([id], [province]) VALUES (56, N'Thừa Thiên Huế')
INSERT [dbo].[Province] ([id], [province]) VALUES (57, N'Tiền Giang')
INSERT [dbo].[Province] ([id], [province]) VALUES (58, N'Trà Vinh')
INSERT [dbo].[Province] ([id], [province]) VALUES (59, N'Tuyên Quang')
INSERT [dbo].[Province] ([id], [province]) VALUES (60, N'Vĩnh Long')
INSERT [dbo].[Province] ([id], [province]) VALUES (61, N'Vĩnh Phúc')
INSERT [dbo].[Province] ([id], [province]) VALUES (62, N'Yên Bái')
INSERT [dbo].[Province] ([id], [province]) VALUES (63, N'TP. Hồ Chí Minh')
SET IDENTITY_INSERT [dbo].[Province] OFF
GO
ALTER TABLE [dbo].[Address]  WITH CHECK ADD  CONSTRAINT [FK_Address_Coordinates] FOREIGN KEY([idcoordinates])
REFERENCES [dbo].[Coordinates] ([id])
GO
ALTER TABLE [dbo].[Address] CHECK CONSTRAINT [FK_Address_Coordinates]
GO
ALTER TABLE [dbo].[Address]  WITH CHECK ADD  CONSTRAINT [FK_Address_District] FOREIGN KEY([iddistrict])
REFERENCES [dbo].[District] ([id])
GO
ALTER TABLE [dbo].[Address] CHECK CONSTRAINT [FK_Address_District]
GO
ALTER TABLE [dbo].[Address]  WITH CHECK ADD  CONSTRAINT [FK_Address_Province] FOREIGN KEY([idprovince])
REFERENCES [dbo].[Province] ([id])
GO
ALTER TABLE [dbo].[Address] CHECK CONSTRAINT [FK_Address_Province]
GO
ALTER TABLE [dbo].[District]  WITH CHECK ADD  CONSTRAINT [FK_District_Province] FOREIGN KEY([idprovince])
REFERENCES [dbo].[Province] ([id])
GO
ALTER TABLE [dbo].[District] CHECK CONSTRAINT [FK_District_Province]
GO
ALTER TABLE [dbo].[Warning]  WITH CHECK ADD  CONSTRAINT [FK_Warning_Address] FOREIGN KEY([idaddress])
REFERENCES [dbo].[Address] ([id])
GO
ALTER TABLE [dbo].[Warning] CHECK CONSTRAINT [FK_Warning_Address]
GO
ALTER TABLE [dbo].[Warning]  WITH CHECK ADD  CONSTRAINT [FK_Warning_User] FOREIGN KEY([iduser])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Warning] CHECK CONSTRAINT [FK_Warning_User]
GO
USE [master]
GO
ALTER DATABASE [mapwarning] SET  READ_WRITE 
GO
