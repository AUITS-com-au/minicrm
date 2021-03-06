USE [master]
GO
/****** Object:  Database [BAJTicketingNew]    Script Date: 3/14/2019 9:53:06 AM ******/
CREATE DATABASE [BAJTicketingNew]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'BAJTicketingNew', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.MSSQLSERVER\MSSQL\DATA\BAJTicketingNew.mdf' , SIZE = 139264KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'BAJTicketingNew_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.MSSQLSERVER\MSSQL\DATA\BAJTicketingNew_log.ldf' , SIZE = 139264KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [BAJTicketingNew] SET COMPATIBILITY_LEVEL = 130
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [BAJTicketingNew].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [BAJTicketingNew] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET ARITHABORT OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [BAJTicketingNew] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [BAJTicketingNew] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET  DISABLE_BROKER 
GO
ALTER DATABASE [BAJTicketingNew] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [BAJTicketingNew] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [BAJTicketingNew] SET  MULTI_USER 
GO
ALTER DATABASE [BAJTicketingNew] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [BAJTicketingNew] SET DB_CHAINING OFF 
GO
ALTER DATABASE [BAJTicketingNew] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [BAJTicketingNew] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [BAJTicketingNew] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'BAJTicketingNew', N'ON'
GO
ALTER DATABASE [BAJTicketingNew] SET QUERY_STORE = OFF
GO
USE [BAJTicketingNew]
GO
ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
USE [BAJTicketingNew]
GO
USE [BAJTicketingNew]
GO
/****** Object:  Sequence [dbo].[ticketNumber]    Script Date: 3/14/2019 9:53:06 AM ******/
CREATE SEQUENCE [dbo].[ticketNumber] 
 AS [bigint]
 START WITH 1
 INCREMENT BY 1
 MINVALUE 1
 MAXVALUE 10000
 CYCLE 
 CACHE 
GO
/****** Object:  UserDefinedTableType [dbo].[PermHolder]    Script Date: 3/14/2019 9:53:06 AM ******/
CREATE TYPE [dbo].[PermHolder] AS TABLE(
	[TopicID] [int] NOT NULL,
	[UserName] [varchar](50) NULL,
	[UserID] [int] NULL,
	[InheritedFrom] [varchar](10) NULL,
	[CreationDate] [datetime] NULL,
	[CreatedBy] [varchar](50) NULL,
	[admin] [tinyint] NULL,
	[canCreate] [tinyint] NULL,
	[canReopen] [tinyint] NULL,
	[canRead] [tinyint] NULL,
	[canDelete] [tinyint] NULL,
	[canReply] [tinyint] NULL,
	[canClose] [tinyint] NULL,
	[canResolve] [tinyint] NULL,
	[canModify] [tinyint] NULL,
	[canRunReport] [tinyint] NULL,
	[canSubscribe] [tinyint] NULL
)
GO
/****** Object:  UserDefinedTableType [dbo].[UsersList]    Script Date: 3/14/2019 9:53:06 AM ******/
CREATE TYPE [dbo].[UsersList] AS TABLE(
	[userID] [int] NULL
)
GO
/****** Object:  UserDefinedFunction [dbo].[WorkTime]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[WorkTime] 
(
    @StartDate DATETIME,
    @FinishDate DATETIME,@WorkStart TIME, @WorkFinish TIME
)
RETURNS BIGINT
AS
BEGIN
    DECLARE @Temp BIGINT
    SET @Temp=0

    DECLARE @FirstDay DATE
    SET @FirstDay = CONVERT(DATE, @StartDate, 112)

    DECLARE @LastDay DATE
    SET @LastDay = CONVERT(DATE, @FinishDate, 112)

    DECLARE @StartTime TIME
    SET @StartTime = CONVERT(TIME, @StartDate)

    DECLARE @FinishTime TIME
    SET @FinishTime = CONVERT(TIME, @FinishDate)
 
    DECLARE @DailyWorkTime BIGINT
    SET @DailyWorkTime = DATEDIFF(MINUTE, @WorkStart, @WorkFinish)

    IF (@StartTime<@WorkStart)
    BEGIN
        SET @StartTime = @WorkStart
    END
    IF (@FinishTime>@WorkFinish)
    BEGIN
        SET @FinishTime=@WorkFinish
    END

    DECLARE @CurrentDate DATE
    SET @CurrentDate = @FirstDay
    DECLARE @LastDate DATE
    SET @LastDate = @LastDay

    WHILE(@CurrentDate<=@LastDate)
    BEGIN    
	IF ('DayOn'=(SELECT 'DayOn' from Calendar where (Convert(date,dt)= CONVERT(date,@CurrentDate))  and (isHoliday=0 and isWeekday=1)))   
       -- IF (DATEPART(dw, @CurrentDate)!=1 AND DATEPART(dw, @CurrentDate)!=7)
        BEGIN
            IF (@CurrentDate!=@FirstDay) AND (@CurrentDate!=@LastDay)
            BEGIN
                SET @Temp = @Temp + @DailyWorkTime
            END
            --IF it starts at startdate and it finishes not this date find diff between work finish and start as minutes
            ELSE IF (@CurrentDate=@FirstDay) AND (@CurrentDate!=@LastDay)
            BEGIN
                SET @Temp = @Temp + DATEDIFF(MINUTE, @StartTime, @WorkFinish)
            END

            ELSE IF (@CurrentDate!=@FirstDay) AND (@CurrentDate=@LastDay)
            BEGIN
                SET @Temp = @Temp + DATEDIFF(MINUTE, @WorkStart, @FinishTime)
            END
            --IF it starts and finishes in the same date
            ELSE IF (@CurrentDate=@FirstDay) AND (@CurrentDate=@LastDay)
            BEGIN
                SET @Temp = DATEDIFF(MINUTE, @StartTime, @FinishTime)
            END
        END
        SET @CurrentDate = DATEADD(day, 1, @CurrentDate)
    END

    -- Return the result of the function
    IF @Temp<0
    BEGIN
        SET @Temp=0
    END
    RETURN @Temp

END
GO
/****** Object:  Table [dbo].[assignedtickets]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[assignedtickets](
	[ID] [bigint] IDENTITY(16,1) NOT NULL,
	[ticketID] [bigint] NULL,
	[userID] [nvarchar](50) NULL,
	[Enabled] [int] NULL,
	[CreationDate] [datetime] NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [varchar](8000) NULL,
 CONSTRAINT [PK_usersassignedtickets_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[attachments]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[attachments](
	[ID] [bigint] IDENTITY(59,1) NOT NULL,
	[FileName] [nvarchar](500) NOT NULL,
	[FileType] [nvarchar](500) NULL,
	[Hash] [nvarchar](50) NULL,
	[RAWContent] [varbinary](max) NULL,
	[FilePath] [nvarchar](500) NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [datetime] NULL,
	[FileDesc] [nvarchar](1000) NULL,
 CONSTRAINT [PK_attachments_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[calendar]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[calendar](
	[dt] [datetime] NOT NULL,
	[isWeekday] [smallint] NULL,
	[isHoliday] [smallint] NULL,
	[Y] [smallint] NULL,
	[FY] [smallint] NULL,
	[Q] [tinyint] NULL,
	[M] [tinyint] NULL,
	[D] [tinyint] NULL,
	[DW] [tinyint] NULL,
	[monthname] [nvarchar](9) NULL,
	[dayname] [nvarchar](9) NULL,
	[W] [tinyint] NULL,
	[Description] [nvarchar](150) NULL,
 CONSTRAINT [PK_calendar_dt] PRIMARY KEY CLUSTERED 
(
	[dt] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[countrycode]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[countrycode](
	[ID] [int] NOT NULL,
	[CountryName] [nvarchar](255) NULL,
	[ISO2] [nvarchar](255) NULL,
	[ISO3] [nvarchar](255) NULL,
	[TopLevelDomain] [nvarchar](255) NULL,
	[FIPS] [nvarchar](255) NULL,
	[ISONumeric] [float] NULL,
	[GeoNameID] [float] NULL,
	[E164] [float] NULL,
	[PhoneCode] [nvarchar](255) NULL,
	[Continent] [nvarchar](255) NULL,
	[Capital] [nvarchar](255) NULL,
	[TimeZoneinCapital] [nvarchar](255) NULL,
	[Currency] [nvarchar](255) NULL,
	[LanguageCodes] [nvarchar](255) NULL,
	[Languages] [nvarchar](255) NULL,
	[AreaKM2] [float] NULL,
	[InternetHosts] [float] NULL,
	[InternetUsers] [float] NULL,
	[Mobile] [float] NULL,
	[Landline] [float] NULL,
	[GDP] [float] NULL,
	[MobileLength] [int] NULL,
	[Enabled] [tinyint] NULL,
 CONSTRAINT [PK_countrycode] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customeraccounts]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customeraccounts](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[CustomerNameAR] [nvarchar](250) NULL,
	[CustomerNameEn] [nvarchar](250) NULL,
	[Email] [varchar](50) NULL,
	[Mobile] [varchar](15) NULL,
	[Nin] [varchar](15) NULL,
	[Segment] [nvarchar](50) NULL,
	[CustomerCIF] [nvarchar](10) NULL,
	[BranchName] [nvarchar](300) NULL,
 CONSTRAINT [PK_CustomerAccounts] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[emailhistory]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[emailhistory](
	[ID] [bigint] IDENTITY(702,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[EmailID] [nvarchar](250) NOT NULL,
	[CopyTo] [nvarchar](max) NULL,
	[BCopyTo] [nvarchar](max) NULL,
	[CreationDate] [datetime] NULL,
	[SendDate] [datetime] NULL,
	[SendingOn] [nvarchar](120) NOT NULL,
	[Status] [int] NULL,
	[Type] [nvarchar](50) NULL,
	[Result] [nvarchar](max) NULL,
	[EmailMessage] [bigint] NULL,
	[ReadStatus] [tinyint] NULL,
	[DeleteStatus] [tinyint] NULL,
	[Tries] [int] NULL,
 CONSTRAINT [PK_emailhistory_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[emailmessage]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[emailmessage](
	[Id] [bigint] IDENTITY(116,1) NOT NULL,
	[EmailTitle] [nvarchar](500) NOT NULL,
	[EmailMessage] [nvarchar](max) NOT NULL,
	[AttachmentsID] [nvarchar](max) NULL,
 CONSTRAINT [PK_emailmessage_Id] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[emailtemplates]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[emailtemplates](
	[TemplateID] [int] NOT NULL,
	[TemplateName] [nvarchar](150) NULL,
	[TemplateTitle] [nvarchar](500) NULL,
	[TemplateData] [nvarchar](max) NULL,
	[enabled] [smallint] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[ModifiedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_emailtemplates_TemplateID] PRIMARY KEY CLUSTERED 
(
	[TemplateID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[escalationhistory]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[escalationhistory](
	[ID] [bigint] IDENTITY(236,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[topicSLA] [int] NOT NULL,
	[ESCDateTime] [datetime] NOT NULL,
 CONSTRAINT [PK_escalationhistory_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[generatedTopicPermissions]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[generatedTopicPermissions](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[TopicID] [int] NOT NULL,
	[UserName] [varchar](50) NULL,
	[UserID] [int] NULL,
	[InheritedFrom] [varchar](10) NULL,
	[CreationDate] [datetime] NULL,
	[CreatedBy] [varchar](50) NULL,
	[admin] [tinyint] NULL,
	[canCreate] [tinyint] NULL,
	[canReopen] [tinyint] NULL,
	[canRead] [tinyint] NULL,
	[canDelete] [tinyint] NULL,
	[canReply] [tinyint] NULL,
	[canClose] [tinyint] NULL,
	[canResolve] [tinyint] NULL,
	[canModify] [tinyint] NULL,
	[canRunReport] [tinyint] NULL,
	[canSubscribe] [tinyint] NULL,
	[canAssign] [tinyint] NULL,
	[canChgDpt] [tinyint] NULL,
 CONSTRAINT [PK_GeneratedTopicPermissions] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[globalconfiguration]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[globalconfiguration](
	[Property] [nvarchar](50) NOT NULL,
	[Value] [ntext] NOT NULL,
	[type] [nvarchar](50) NULL,
 CONSTRAINT [PK_globalconfiguration_Property] PRIMARY KEY CLUSTERED 
(
	[Property] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[grouproles]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[grouproles](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[groupID] [int] NOT NULL,
	[roleID] [int] NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_groupspermissions] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[groups]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[groups](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[GroupName] [nvarchar](50) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[ModificationDate] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[Enabled] [tinyint] NOT NULL,
 CONSTRAINT [PK_group_GroupID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[holdreason]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[holdreason](
	[ReasonID] [int] IDENTITY(3,1) NOT NULL,
	[SortOrder] [int] NULL,
	[ArabicLabel] [nvarchar](150) NULL,
	[EnglishLabel] [nvarchar](150) NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[HoldTime] [int] NULL,
	[Flexible] [tinyint] NULL,
 CONSTRAINT [PK_ticketholdreason_ReasonID] PRIMARY KEY CLUSTERED 
(
	[ReasonID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[holidays]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[holidays](
	[HolidayID] [int] IDENTITY(1,1) NOT NULL,
	[HolidayName] [nvarchar](250) NULL,
	[StartDate] [date] NOT NULL,
	[EndData] [date] NOT NULL,
	[Enabled] [smallint] NOT NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [datetime] NULL,
 CONSTRAINT [PK_holidays_HolidayID] PRIMARY KEY CLUSTERED 
(
	[HolidayID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [holidays$IX_Holidays] UNIQUE NONCLUSTERED 
(
	[Enabled] ASC,
	[StartDate] ASC,
	[EndData] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[logindetails]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[logindetails](
	[ID] [bigint] IDENTITY(1778,1) NOT NULL,
	[UserID] [nvarchar](50) NOT NULL,
	[DateTime] [datetime] NOT NULL,
	[Result] [smallint] NOT NULL,
	[IPAddress] [varchar](20) NULL,
	[LogoutDateTime] [datetime] NULL,
	[Token] [varchar](500) NULL,
	[LogoutReason] [varchar](100) NULL,
 CONSTRAINT [PK_logindetails_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[maincategory]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[maincategory](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ArabicLabel] [nvarchar](150) NOT NULL,
	[EnglishLabel] [nvarchar](150) NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[Enabled] [tinyint] NULL,
	[Configuration] [nvarchar](max) NULL,
 CONSTRAINT [PK_maincategory_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[maincatholidays]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[maincatholidays](
	[Id] [int] IDENTITY(1,1) NOT NULL,
	[MainCategory] [int] NOT NULL,
	[HolidayID] [int] NOT NULL,
 CONSTRAINT [PK_maincatholidays_Id] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[notifications]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notifications](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[DataID] [bigint] NULL,
	[Action] [int] NULL,
	[CreationDate] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[Status] [tinyint] NULL,
 CONSTRAINT [PK_NotificationMessages] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[permissions]    Script Date: 3/14/2019 9:53:06 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[permissions](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Permission] [varchar](50) NOT NULL,
	[ModuleName] [varchar](50) NULL,
	[GroupName] [varchar](50) NULL,
	[Description] [nvarchar](150) NULL,
 CONSTRAINT [PK_Permissions] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rolepermissions]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rolepermissions](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[roleID] [int] NOT NULL,
	[permissionID] [int] NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_rolepermissions] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Role] [varchar](50) NOT NULL,
	[CreatedBy] [varchar](255) NULL,
	[CreationDate] [datetime2](7) NULL,
	[ModificationDate] [datetime2](7) NULL,
	[ModifiedBy] [varchar](255) NULL,
 CONSTRAINT [PK_roles] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sla]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sla](
	[ID] [int] IDENTITY(5000,1) NOT NULL,
	[Time] [int] NOT NULL,
	[CreationDate] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [datetime] NULL,
	[SLAName] [nvarchar](50) NULL,
	[CatID] [int] NULL,
 CONSTRAINT [PK_sla_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[slausers]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[slausers](
	[id] [bigint] IDENTITY(72,1) NOT NULL,
	[UserId] [nvarchar](50) NULL,
	[Topicsla] [int] NOT NULL,
	[CreatedBy] [nvarchar](45) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[ModifiedBy] [nvarchar](45) NULL,
	[ModificationDate] [datetime] NULL,
	[Enabled] [tinyint] NOT NULL,
	[Emails] [nvarchar](max) NULL,
	[MobileNumbers] [nvarchar](max) NULL,
 CONSTRAINT [PK_userssla_id] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[smsHistory]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[smsHistory](
	[ID] [bigint] IDENTITY(11,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[Mobile] [nvarchar](20) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[Message] [nvarchar](max) NOT NULL,
	[TemplateID] [int] NULL,
	[Status] [int] NOT NULL,
	[SendDate] [datetime] NULL,
	[SendingON] [nvarchar](50) NULL,
	[ErrorCode] [nvarchar](max) NULL,
	[BackEndID] [nvarchar](50) NULL,
 CONSTRAINT [PK_smshistory_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[smsTemplate]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[smsTemplate](
	[ID] [int] IDENTITY(44,1) NOT NULL,
	[Enabled] [tinyint] NULL,
	[TemplateName] [nvarchar](50) NOT NULL,
	[CreationDate] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModificationDate] [datetime] NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[TemplateText] [nvarchar](700) NULL,
 CONSTRAINT [PK_smstemplate_TemplateID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sourceChannel]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sourceChannel](
	[ChannelID] [int] NOT NULL,
	[ArabicLabel] [nvarchar](150) NOT NULL,
	[EnglishLabel] [nvarchar](150) NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[Enabled] [tinyint] NULL,
 CONSTRAINT [PK_sourcechannel_ChannelID] PRIMARY KEY CLUSTERED 
(
	[ChannelID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[status]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[status](
	[ID] [int] NOT NULL,
	[ListOrder] [int] NULL,
	[Enabled] [tinyint] NULL,
	[EnableSLA] [tinyint] NULL,
	[DisplayOnTicketEdit] [tinyint] NULL,
	[ArabicLabel] [nvarchar](150) NOT NULL,
	[EnglishLabel] [nvarchar](150) NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[AvailableActions] [varchar](150) NULL,
 CONSTRAINT [PK_status_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[subcategory]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[subcategory](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[MainCategory] [int] NOT NULL,
	[ArabicLabel] [nvarchar](150) NOT NULL,
	[EnglishLabel] [nvarchar](150) NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[Enabled] [tinyint] NULL,
 CONSTRAINT [PK_subcategory_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[subscriptions]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[subscriptions](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Topic] [int] NULL,
	[TicketID] [bigint] NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[userID] [nvarchar](50) NULL,
	[subStatus] [tinyint] NULL,
	[OnCreate] [tinyint] NULL,
	[OnUpdate] [tinyint] NULL,
	[OnClose] [tinyint] NULL,
	[EmailID] [nvarchar](250) NULL,
 CONSTRAINT [PK_Subscriptions] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticket]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticket](
	[ID] [bigint] NOT NULL,
	[Topic] [int] NULL,
	[OriginalTopic] [int] NULL,
	[CurrentStatus] [int] NOT NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NOT NULL,
	[ModificationDate] [datetime] NULL,
	[CrossedAllSLA] [tinyint] NULL,
	[CustomerAccount] [bigint] NULL,
	[SourceChannel] [int] NULL,
	[Subject] [nvarchar](300) NULL,
	[TicketType] [int] NOT NULL,
	[Details] [nvarchar](max) NULL,
	[AssignedTo] [nvarchar](50) NULL,
	[EscalationCalDate] [datetime] NULL,
	[LastSLA] [int] NULL,
	[Language] [int] NULL,
	[Priority] [int] NULL,
	[LastTicketData] [bigint] NULL,
	[Solved] [tinyint] NULL,
	[Closed] [tinyint] NULL,
	[Deleted] [tinyint] NULL,
	[NumberOfCrossedSLA] [int] NULL,
	[TotalCrossedTime] [bigint] NULL,
	[NumberOfSLA] [int] NULL,
 CONSTRAINT [PK_ticket_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticketactions]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticketactions](
	[ActionID] [int] NOT NULL,
	[ArabicLabel] [nvarchar](150) NULL,
	[EnglishLabel] [nvarchar](150) NULL,
	[Enabled] [tinyint] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[SetStatusTo] [int] NULL,
 CONSTRAINT [PK_ticketactions_ActionID] PRIMARY KEY CLUSTERED 
(
	[ActionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TicketAttachments]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TicketAttachments](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[DataID] [bigint] NULL,
	[AttachmentID] [bigint] NOT NULL,
 CONSTRAINT [PK_TicketAttachments] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticketdata]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticketdata](
	[ID] [bigint] IDENTITY(58,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[ActionID] [int] NULL,
	[Title] [nvarchar](400) NULL,
	[TicketData] [nvarchar](max) NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [datetime] NULL,
	[Notes] [nvarchar](max) NULL,
	[Hidden] [tinyint] NULL,
	[NotificationID] [bigint] NULL,
	[Notify] [tinyint] NULL,
	[NotfiyStatus] [int] NULL,
 CONSTRAINT [PK_ticketdata_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticketExtData]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticketExtData](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[BranchName] [nvarchar](500) NULL,
	[TransactionCode] [nvarchar](50) NULL,
	[TransactionDesc] [nvarchar](max) NULL,
	[TransactionType] [nvarchar](50) NULL,
	[CustomerAccount] [nvarchar](50) NULL,
	[Amount] [nvarchar](20) NULL,
	[TraxDateTime] [datetime] NULL,
	[ExtDate1] [datetime] NULL,
	[ExtDate2] [datetime] NULL,
	[ExtField1] [nvarchar](250) NULL,
	[ExtField2] [nvarchar](250) NULL,
	[ExtField3] [nvarchar](250) NULL,
	[ExtField4] [nvarchar](250) NULL,
	[ExtField5] [nvarchar](250) NULL,
	[ExtField6] [nvarchar](250) NULL,
	[ExtField7] [nvarchar](250) NULL,
	[ExtField8] [nvarchar](250) NULL,
	[ExtField9] [nvarchar](250) NULL,
	[ExtField10] [nvarchar](250) NULL,
	[ExtField11] [nvarchar](250) NULL,
	[ExtField12] [nvarchar](250) NULL,
	[ExtField13] [nvarchar](250) NULL,
	[ExtField14] [nvarchar](250) NULL,
	[ExtField15] [nvarchar](250) NULL,
	[ExtField16] [nvarchar](250) NULL,
	[ExtField17] [nvarchar](250) NULL,
	[ExtField18] [nvarchar](250) NULL,
	[ExtField19] [nvarchar](250) NULL,
	[ExtField20] [nvarchar](250) NULL,
 CONSTRAINT [PK_ticketExtData] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[TicketHistory]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[TicketHistory](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ActionID] [int] NULL,
	[DataID] [bigint] NULL,
	[OldStatus] [int] NULL,
	[NewStatus] [int] NULL,
	[OldTopic] [int] NULL,
	[NewTopic] [int] NULL,
	[OldAssigne] [nvarchar](50) NULL,
	[NewAssigne] [nvarchar](50) NULL,
	[EscalationHisID] [bigint] NULL,
 CONSTRAINT [PK_TicketHistory] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticketlock]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticketlock](
	[LockID] [bigint] IDENTITY(113,1) NOT NULL,
	[TicketID] [bigint] NOT NULL,
	[UserID] [nvarchar](50) NOT NULL,
	[DateTime] [datetime] NOT NULL,
	[ExpiresOn] [datetime] NOT NULL,
	[Expired] [tinyint] NULL,
 CONSTRAINT [PK_ticketlock_LockID] PRIMARY KEY CLUSTERED 
(
	[LockID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tickettypes]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tickettypes](
	[TypeID] [int] NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [datetime] NULL,
	[ArabicLabel] [nvarchar](150) NOT NULL,
	[EnglishLabel] [nvarchar](150) NOT NULL,
	[Enabled] [tinyint] NULL,
 CONSTRAINT [PK_type_TypeID] PRIMARY KEY CLUSTERED 
(
	[TypeID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[topic]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[topic](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[SubCategory] [int] NOT NULL,
	[ArabicLabel] [nvarchar](150) NOT NULL,
	[EnglishLabel] [nvarchar](150) NOT NULL,
	[CreationDate] [datetime] NULL,
	[ModificationDate] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[Configuration] [nvarchar](max) NOT NULL,
	[Enabled] [tinyint] NULL,
	[Principals] [nvarchar](max) NULL,
 CONSTRAINT [PK_topic_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[topicsla]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[topicsla](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[TopicID] [int] NOT NULL,
	[SLAID] [int] NOT NULL,
	[SLALevel] [int] NOT NULL,
	[CreationDate] [datetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [datetime] NULL,
 CONSTRAINT [PK_TopicSLA_1] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[topicspermissions]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[topicspermissions](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[assigne] [int] NOT NULL,
	[type] [varchar](10) NOT NULL,
	[topicId] [int] NOT NULL,
	[admin] [tinyint] NOT NULL,
	[canCreate] [tinyint] NOT NULL,
	[canChgDpt] [tinyint] NOT NULL,
	[canReopen] [tinyint] NOT NULL,
	[canRead] [tinyint] NOT NULL,
	[canDelete] [tinyint] NOT NULL,
	[canReply] [tinyint] NOT NULL,
	[canClose] [tinyint] NOT NULL,
	[canResolve] [tinyint] NOT NULL,
	[canModify] [tinyint] NOT NULL,
	[canRunReport] [tinyint] NOT NULL,
	[canSubscribe] [tinyint] NOT NULL,
	[canAssign] [tinyint] NOT NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [datetime] NULL,
 CONSTRAINT [PK_TopicsPermissions] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usergroups]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usergroups](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[CreationDate] [datetime] NULL,
	[groupID] [int] NOT NULL,
	[userID] [int] NOT NULL,
 CONSTRAINT [PK__usergrou__3214EC27C36D7FE9] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userpreferences]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userpreferences](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[ModificationDate] [varchar](8000) NULL,
	[EmailsNotifications] [tinyint] NOT NULL,
	[Language] [varchar](2) NULL,
	[Avatar] [image] NULL,
	[SLAEmails] [tinyint] NULL,
	[TicketCreationEmails] [tinyint] NULL,
	[TicketEditEmails] [tinyint] NULL,
	[TicketAssignEmails] [tinyint] NULL,
	[TicketCLoseEmails] [tinyint] NULL,
	[EmailDigest] [tinyint] NULL,
	[IncludeAttatchments] [tinyint] NULL,
 CONSTRAINT [PK_userPreferences] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userroles]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userroles](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[userID] [int] NOT NULL,
	[roleID] [int] NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[CreatedBy] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_userspermissions] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [nvarchar](50) NOT NULL,
	[FirstName] [nvarchar](500) NOT NULL,
	[LastName] [nvarchar](500) NULL,
	[Email] [nvarchar](250) NULL,
	[Password] [nvarchar](256) NULL,
	[StaffID] [nvarchar](20) NULL,
	[Enabled] [tinyint] NULL,
	[CreationDate] [varchar](8000) NULL,
	[ModificationDate] [varchar](8000) NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[ModifiedBy] [nvarchar](50) NULL,
	[LoginAttempts] [int] NULL,
	[SystemUser] [tinyint] NULL,
	[Title] [nvarchar](150) NULL,
	[Department] [nvarchar](50) NULL,
	[LDAPUser] [tinyint] NULL,
 CONSTRAINT [PK_users_1] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usertopic]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usertopic](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [nvarchar](50) NOT NULL,
	[TopicID] [int] NOT NULL,
	[CreatedBy] [nvarchar](45) NOT NULL,
	[CreationDate] [datetime] NOT NULL,
	[ModifiedBy] [nvarchar](45) NULL,
	[ModificationDate] [datetime] NULL,
	[Status] [tinyint] NOT NULL,
 CONSTRAINT [PK_usertopic] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_customeraccounts]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_customeraccounts] ON [dbo].[customeraccounts]
(
	[Nin] ASC,
	[Segment] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_customeraccounts_1]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE NONCLUSTERED INDEX [IX_customeraccounts_1] ON [dbo].[customeraccounts]
(
	[Mobile] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_customeraccounts_2]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE NONCLUSTERED INDEX [IX_customeraccounts_2] ON [dbo].[customeraccounts]
(
	[CustomerNameAR] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_customeraccounts_3]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE NONCLUSTERED INDEX [IX_customeraccounts_3] ON [dbo].[customeraccounts]
(
	[CustomerNameEn] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_generatedTopicPermissions]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE NONCLUSTERED INDEX [IX_generatedTopicPermissions] ON [dbo].[generatedTopicPermissions]
(
	[TopicID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_generatedTopicPermissions_1]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE NONCLUSTERED INDEX [IX_generatedTopicPermissions_1] ON [dbo].[generatedTopicPermissions]
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_generatedTopicPermissions_2]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE NONCLUSTERED INDEX [IX_generatedTopicPermissions_2] ON [dbo].[generatedTopicPermissions]
(
	[UserName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_groups]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_groups] ON [dbo].[groups]
(
	[GroupName] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_maincategory_name]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_maincategory_name] ON [dbo].[maincategory]
(
	[ArabicLabel] ASC,
	[EnglishLabel] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_roles]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_roles] ON [dbo].[roles]
(
	[Role] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_subcategory_name]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_subcategory_name] ON [dbo].[subcategory]
(
	[EnglishLabel] ASC,
	[MainCategory] ASC,
	[ArabicLabel] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_topic_name]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_topic_name] ON [dbo].[topic]
(
	[EnglishLabel] ASC,
	[ArabicLabel] ASC,
	[SubCategory] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_TopicSLA]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_TopicSLA] ON [dbo].[topicsla]
(
	[TopicID] ASC,
	[SLALevel] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_topicspermissions]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE NONCLUSTERED INDEX [IX_topicspermissions] ON [dbo].[topicspermissions]
(
	[assigne] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [IX_userpreferences]    Script Date: 3/14/2019 9:53:07 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_userpreferences] ON [dbo].[userpreferences]
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[assignedtickets] ADD  CONSTRAINT [DF__usersassi__Ticke__6ABAD62E]  DEFAULT (NULL) FOR [ticketID]
GO
ALTER TABLE [dbo].[assignedtickets] ADD  CONSTRAINT [DF__usersassi__Assig__6BAEFA67]  DEFAULT (NULL) FOR [userID]
GO
ALTER TABLE [dbo].[assignedtickets] ADD  CONSTRAINT [DF__usersassi__Statu__67DE6983]  DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[assignedtickets] ADD  CONSTRAINT [DF__usersassi__Creat__66EA454A]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[assignedtickets] ADD  CONSTRAINT [DF__usersassi__Modif__68D28DBC]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[assignedtickets] ADD  CONSTRAINT [DF__usersassi__Modif__69C6B1F5]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[attachments] ADD  CONSTRAINT [DF__attachmen__FileT__4E3E9311]  DEFAULT (NULL) FOR [FileType]
GO
ALTER TABLE [dbo].[attachments] ADD  CONSTRAINT [DF__attachment__Hash__4F32B74A]  DEFAULT (NULL) FOR [Hash]
GO
ALTER TABLE [dbo].[attachments] ADD  CONSTRAINT [DF__attachmen__Creat__5026DB83]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [isWeekday]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [isHoliday]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [Y]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [FY]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [Q]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [M]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [D]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [DW]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [monthname]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [dayname]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [W]
GO
ALTER TABLE [dbo].[calendar] ADD  DEFAULT (NULL) FOR [Description]
GO
ALTER TABLE [dbo].[customeraccounts] ADD  CONSTRAINT [DF_CustomerAccounts_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[customeraccounts] ADD  CONSTRAINT [DF_CustomerAccounts_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[customeraccounts] ADD  CONSTRAINT [DF_CustomerAccounts_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[customeraccounts] ADD  CONSTRAINT [DF_CustomerAccounts_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[emailhistory] ADD  CONSTRAINT [DF__emailhist__Creat__30792600]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[emailhistory] ADD  CONSTRAINT [DF__emailhist__SendD__316D4A39]  DEFAULT (NULL) FOR [SendDate]
GO
ALTER TABLE [dbo].[emailhistory] ADD  CONSTRAINT [DF__emailhisto__Type__32616E72]  DEFAULT (NULL) FOR [Type]
GO
ALTER TABLE [dbo].[emailhistory] ADD  CONSTRAINT [DF__emailhist__Email__335592AB]  DEFAULT (NULL) FOR [EmailMessage]
GO
ALTER TABLE [dbo].[emailhistory] ADD  CONSTRAINT [DF_emailhistory_Tries]  DEFAULT ((0)) FOR [Tries]
GO
ALTER TABLE [dbo].[emailtemplates] ADD  CONSTRAINT [DF__emailtemp__Templ__45BE5BA9]  DEFAULT (NULL) FOR [TemplateName]
GO
ALTER TABLE [dbo].[emailtemplates] ADD  CONSTRAINT [DF__emailtemp__enabl__44CA3770]  DEFAULT (NULL) FOR [enabled]
GO
ALTER TABLE [dbo].[emailtemplates] ADD  CONSTRAINT [DF__emailtemp__Creat__46B27FE2]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[emailtemplates] ADD  CONSTRAINT [DF__emailtemp__Creat__47A6A41B]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[emailtemplates] ADD  CONSTRAINT [DF__emailtemp__Modif__489AC854]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[emailtemplates] ADD  CONSTRAINT [DF__emailtemp__Modif__498EEC8D]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_admin]  DEFAULT ((0)) FOR [admin]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canCreate]  DEFAULT ((0)) FOR [canCreate]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canReopen]  DEFAULT ((0)) FOR [canReopen]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canRead]  DEFAULT ((0)) FOR [canRead]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canDelete]  DEFAULT ((0)) FOR [canDelete]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canReply]  DEFAULT ((0)) FOR [canReply]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canClose]  DEFAULT ((0)) FOR [canClose]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canResolve]  DEFAULT ((0)) FOR [canResolve]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canModify]  DEFAULT ((0)) FOR [canModify]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canRunReport]  DEFAULT ((0)) FOR [canRunReport]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canSubscribe]  DEFAULT ((0)) FOR [canSubscribe]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canAssign]  DEFAULT ((0)) FOR [canAssign]
GO
ALTER TABLE [dbo].[generatedTopicPermissions] ADD  CONSTRAINT [DF_generatedTopicPermissions_canChgDpt]  DEFAULT ((0)) FOR [canChgDpt]
GO
ALTER TABLE [dbo].[groups] ADD  CONSTRAINT [DF__groups__Modifica__4A8310C6]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[groups] ADD  CONSTRAINT [DF__groups__Modified__4B7734FF]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[groups] ADD  CONSTRAINT [DF_group_Enabled]  DEFAULT ((1)) FOR [Enabled]
GO
ALTER TABLE [dbo].[holdreason] ADD  CONSTRAINT [DF__tickethol__SortO__3DE82FB7]  DEFAULT (NULL) FOR [SortOrder]
GO
ALTER TABLE [dbo].[holdreason] ADD  CONSTRAINT [DF__tickethol__Arabi__3BFFE745]  DEFAULT (NULL) FOR [ArabicLabel]
GO
ALTER TABLE [dbo].[holdreason] ADD  CONSTRAINT [DF__tickethol__Engli__3CF40B7E]  DEFAULT (NULL) FOR [EnglishLabel]
GO
ALTER TABLE [dbo].[holdreason] ADD  CONSTRAINT [DF_ticketholdreason_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[holdreason] ADD  CONSTRAINT [DF_ticketholdreason_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[holdreason] ADD  CONSTRAINT [DF_ticketholdreason_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[holdreason] ADD  CONSTRAINT [DF_ticketholdreason_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[holidays] ADD  CONSTRAINT [DF__holidays__Holida__4C6B5938]  DEFAULT (NULL) FOR [HolidayName]
GO
ALTER TABLE [dbo].[holidays] ADD  CONSTRAINT [DF_holidays_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[maincategory] ADD  CONSTRAINT [DF__maincateg__Creat__08362A7C]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[maincategory] ADD  CONSTRAINT [DF__maincateg__Updat__092A4EB5]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[maincategory] ADD  CONSTRAINT [DF__maincateg__Creat__0A1E72EE]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[maincategory] ADD  CONSTRAINT [DF__maincateg__Modif__0B129727]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[notifications] ADD  CONSTRAINT [DF_NotificationMessages_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[notifications] ADD  CONSTRAINT [DF_NotificationMessages_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[sla] ADD  CONSTRAINT [DF__sla__CreationDat__5669C4BE]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[sla] ADD  CONSTRAINT [DF__sla__Creator__575DE8F7]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[sla] ADD  CONSTRAINT [DF__sla__UpdatedBy__58520D30]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[sla] ADD  CONSTRAINT [DF__sla__UpdatedDate__59463169]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[slausers] ADD  CONSTRAINT [DF__userssla__UserId__74B941B4]  DEFAULT (NULL) FOR [UserId]
GO
ALTER TABLE [dbo].[slausers] ADD  CONSTRAINT [DF__userssla__Sla__75AD65ED]  DEFAULT (NULL) FOR [Topicsla]
GO
ALTER TABLE [dbo].[slausers] ADD  CONSTRAINT [DF__userssla__Create__76A18A26]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[slausers] ADD  CONSTRAINT [DF__userssla__Creati__7795AE5F]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[slausers] ADD  CONSTRAINT [DF__userssla__Modifi__7889D298]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[slausers] ADD  CONSTRAINT [DF__userssla__Modifi__797DF6D1]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[smsHistory] ADD  CONSTRAINT [DF__smshistor__SendD__5C229E14]  DEFAULT (NULL) FOR [SendDate]
GO
ALTER TABLE [dbo].[smsHistory] ADD  CONSTRAINT [DF__smshistor__Sendi__5D16C24D]  DEFAULT (NULL) FOR [SendingON]
GO
ALTER TABLE [dbo].[smsTemplate] ADD  CONSTRAINT [DF__smstempla__enabl__6CD828CA]  DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[smsTemplate] ADD  CONSTRAINT [DF__smstempla__Creat__6DCC4D03]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[smsTemplate] ADD  CONSTRAINT [DF__smstempla__Creat__6FB49575]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[smsTemplate] ADD  CONSTRAINT [DF__smstempla__Updat__6EC0713C]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[smsTemplate] ADD  CONSTRAINT [DF__smstempla__Updat__70A8B9AE]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[sourceChannel] ADD  CONSTRAINT [DF_sourceChannel_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[sourceChannel] ADD  CONSTRAINT [DF_sourceChannel_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[sourceChannel] ADD  CONSTRAINT [DF_sourceChannel_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[sourceChannel] ADD  CONSTRAINT [DF_sourceChannel_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[status] ADD  CONSTRAINT [DF__status__OrderBy__719CDDE7]  DEFAULT (NULL) FOR [ListOrder]
GO
ALTER TABLE [dbo].[status] ADD  CONSTRAINT [DF__status__Enabled__72910220]  DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[status] ADD  CONSTRAINT [DF__status__EnableSL__74794A92]  DEFAULT (NULL) FOR [EnableSLA]
GO
ALTER TABLE [dbo].[status] ADD  CONSTRAINT [DF__status__DisplayO__756D6ECB]  DEFAULT (NULL) FOR [DisplayOnTicketEdit]
GO
ALTER TABLE [dbo].[status] ADD  CONSTRAINT [DF_status_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[status] ADD  CONSTRAINT [DF_status_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[status] ADD  CONSTRAINT [DF_status_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[status] ADD  CONSTRAINT [DF_status_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[subcategory] ADD  CONSTRAINT [DF__subcatego__Creat__7306036C]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[subcategory] ADD  CONSTRAINT [DF__subcatego__Updat__73FA27A5]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[subcategory] ADD  CONSTRAINT [DF__subcatego__Creat__74EE4BDE]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[subcategory] ADD  CONSTRAINT [DF__subcatego__Modif__75E27017]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[subscriptions] ADD  CONSTRAINT [DF_Subscriptions_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[subscriptions] ADD  CONSTRAINT [DF_Subscriptions_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[subscriptions] ADD  CONSTRAINT [DF_Subscriptions_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[subscriptions] ADD  CONSTRAINT [DF_Subscriptions_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Topic__78BEDCC2]  DEFAULT (NULL) FOR [Topic]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Status__79B300FB]  DEFAULT (NULL) FOR [CurrentStatus]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Creator__7AA72534]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Modifier__7C8F6DA6]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__UpdateDa__7D8391DF]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__CrossedS__7B9B496D]  DEFAULT ((0)) FOR [CrossedAllSLA]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Customer__7E77B618]  DEFAULT ((1)) FOR [CustomerAccount]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__SourceCh__033C6B35]  DEFAULT (NULL) FOR [SourceChannel]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Title__04308F6E]  DEFAULT (NULL) FOR [Subject]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Type__0524B3A7]  DEFAULT (NULL) FOR [TicketType]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Assigned__0CC5D56F]  DEFAULT (NULL) FOR [AssignedTo]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Escalati__0DB9F9A8]  DEFAULT (NULL) FOR [EscalationCalDate]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__LastSLA__0EAE1DE1]  DEFAULT (NULL) FOR [LastSLA]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Language__0FA2421A]  DEFAULT (NULL) FOR [Language]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF__ticket__Priority__10966653]  DEFAULT (NULL) FOR [Priority]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF_ticket_Solved]  DEFAULT ((0)) FOR [Solved]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF_ticket_Closed]  DEFAULT ((0)) FOR [Closed]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF_ticket_Deleted]  DEFAULT ((0)) FOR [Deleted]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF_ticket_NumberOfCrossedSLA]  DEFAULT ((0)) FOR [NumberOfCrossedSLA]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF_ticket_TotalCrossedTime]  DEFAULT ((0)) FOR [TotalCrossedTime]
GO
ALTER TABLE [dbo].[ticket] ADD  CONSTRAINT [DF_ticket_NumberOfSLA]  DEFAULT ((0)) FOR [NumberOfSLA]
GO
ALTER TABLE [dbo].[ticketactions] ADD  CONSTRAINT [DF__ticketact__Arabi__2BC97F7C]  DEFAULT (NULL) FOR [ArabicLabel]
GO
ALTER TABLE [dbo].[ticketactions] ADD  CONSTRAINT [DF__ticketact__Engli__2CBDA3B5]  DEFAULT (NULL) FOR [EnglishLabel]
GO
ALTER TABLE [dbo].[ticketactions] ADD  CONSTRAINT [DF__ticketact__Enabl__2DB1C7EE]  DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[ticketactions] ADD  CONSTRAINT [DF_ticketaction_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[ticketactions] ADD  CONSTRAINT [DF_ticketaction_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[ticketactions] ADD  CONSTRAINT [DF_ticketaction_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[ticketactions] ADD  CONSTRAINT [DF_ticketaction_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[ticketdata] ADD  CONSTRAINT [DF_ticketdata_ActionID]  DEFAULT (NULL) FOR [ActionID]
GO
ALTER TABLE [dbo].[ticketdata] ADD  CONSTRAINT [DF__ticketdat__Creat__2779CBAB]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[ticketdata] ADD  CONSTRAINT [DF__ticketdat__Creat__286DEFE4]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[ticketdata] ADD  CONSTRAINT [DF__ticketdat__Modif__2C3E80C8]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[ticketdata] ADD  CONSTRAINT [DF__ticketdat__Modif__2D32A501]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[topic] ADD  CONSTRAINT [DF__topic__CreationD__3B80C458]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[topic] ADD  CONSTRAINT [DF__topic__Modificat__3C74E891]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[topic] ADD  CONSTRAINT [DF__topic__CreatedBy__3D690CCA]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[topic] ADD  CONSTRAINT [DF__topic__ModifiedB__3E5D3103]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[topicsla] ADD  CONSTRAINT [DF_TopicSLA_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[topicsla] ADD  CONSTRAINT [DF_TopicSLA_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[topicsla] ADD  CONSTRAINT [DF_TopicSLA_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[topicsla] ADD  CONSTRAINT [DF_TopicSLA_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_admin]  DEFAULT ((0)) FOR [admin]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canCreate]  DEFAULT ((0)) FOR [canCreate]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canChgDpt_1]  DEFAULT ((0)) FOR [canChgDpt]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canReopen]  DEFAULT ((0)) FOR [canReopen]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canRead]  DEFAULT ((0)) FOR [canRead]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canDelete]  DEFAULT ((0)) FOR [canDelete]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canReply]  DEFAULT ((0)) FOR [canReply]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canClose]  DEFAULT ((0)) FOR [canClose]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canResolve]  DEFAULT ((0)) FOR [canResolve]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canModify]  DEFAULT ((0)) FOR [canModify]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canRunReport]  DEFAULT ((0)) FOR [canRunReport]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canSubscribe]  DEFAULT ((0)) FOR [canSubscribe]
GO
ALTER TABLE [dbo].[topicspermissions] ADD  CONSTRAINT [DF_topicspermissions_canAssign]  DEFAULT ((0)) FOR [canAssign]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userPreferences_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userPreferences_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userPreferences_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userPreferences_DisableEmails]  DEFAULT ((0)) FOR [EmailsNotifications]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userpreferences_TicketCreationEmails]  DEFAULT ((0)) FOR [TicketCreationEmails]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userpreferences_TicketEditEmails]  DEFAULT ((0)) FOR [TicketEditEmails]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userpreferences_TicketAssignEmails]  DEFAULT ((0)) FOR [TicketAssignEmails]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userpreferences_TicketCLoseEmails]  DEFAULT ((0)) FOR [TicketCLoseEmails]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userpreferences_EmailDigest]  DEFAULT ((0)) FOR [EmailDigest]
GO
ALTER TABLE [dbo].[userpreferences] ADD  CONSTRAINT [DF_userpreferences_IncludeAttatchments]  DEFAULT ((0)) FOR [IncludeAttatchments]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__Email__4F87BD05]  DEFAULT (NULL) FOR [Email]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__StaffID__507BE13E]  DEFAULT (NULL) FOR [StaffID]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__Enabled__51700577]  DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__CreationD__5634BA94]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__Modificat__5728DECD]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__CreatedBy__581D0306]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__updatedBy__5911273F]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__LoginAtte__5AF96FB1]  DEFAULT ((3)) FOR [LoginAttempts]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__SystemUse__5BED93EA]  DEFAULT ((0)) FOR [SystemUser]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__Title__5DD5DC5C]  DEFAULT (NULL) FOR [Title]
GO
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [DF__users__Departmen__5ECA0095]  DEFAULT (NULL) FOR [Department]
GO
ALTER TABLE [dbo].[usertopic] ADD  CONSTRAINT [DF_usertopic_CreatedBy]  DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[usertopic] ADD  CONSTRAINT [DF_usertopic_CreationDate]  DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[usertopic] ADD  CONSTRAINT [DF_usertopic_ModifiedBy]  DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[usertopic] ADD  CONSTRAINT [DF_usertopic_ModificationDate]  DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[emailhistory]  WITH NOCHECK ADD  CONSTRAINT [FK_EmailHistory_EmailMessage] FOREIGN KEY([EmailMessage])
REFERENCES [dbo].[emailmessage] ([Id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[emailhistory] CHECK CONSTRAINT [FK_EmailHistory_EmailMessage]
GO
ALTER TABLE [dbo].[escalationhistory]  WITH NOCHECK ADD  CONSTRAINT [FK_EscalationHistory_Ticket] FOREIGN KEY([TicketID])
REFERENCES [dbo].[ticket] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[escalationhistory] CHECK CONSTRAINT [FK_EscalationHistory_Ticket]
GO
ALTER TABLE [dbo].[escalationhistory]  WITH NOCHECK ADD  CONSTRAINT [FK_EsclationHistory_SLA] FOREIGN KEY([topicSLA])
REFERENCES [dbo].[topicsla] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[escalationhistory] CHECK CONSTRAINT [FK_EsclationHistory_SLA]
GO
ALTER TABLE [dbo].[generatedTopicPermissions]  WITH CHECK ADD  CONSTRAINT [FK_generatedTopicPermissions_users] FOREIGN KEY([UserID])
REFERENCES [dbo].[users] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[generatedTopicPermissions] CHECK CONSTRAINT [FK_generatedTopicPermissions_users]
GO
ALTER TABLE [dbo].[maincatholidays]  WITH NOCHECK ADD  CONSTRAINT [FK_MainCatHolidays_Holidays] FOREIGN KEY([HolidayID])
REFERENCES [dbo].[holidays] ([HolidayID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[maincatholidays] CHECK CONSTRAINT [FK_MainCatHolidays_Holidays]
GO
ALTER TABLE [dbo].[maincatholidays]  WITH NOCHECK ADD  CONSTRAINT [FK_MainCatHolidays_MainCategory] FOREIGN KEY([MainCategory])
REFERENCES [dbo].[maincategory] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[maincatholidays] CHECK CONSTRAINT [FK_MainCatHolidays_MainCategory]
GO
ALTER TABLE [dbo].[rolepermissions]  WITH CHECK ADD  CONSTRAINT [FK_rolepermissions_permissions] FOREIGN KEY([permissionID])
REFERENCES [dbo].[permissions] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[rolepermissions] CHECK CONSTRAINT [FK_rolepermissions_permissions]
GO
ALTER TABLE [dbo].[smsHistory]  WITH NOCHECK ADD  CONSTRAINT [FK_SMSHistory_Ticket] FOREIGN KEY([TicketID])
REFERENCES [dbo].[ticket] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[smsHistory] CHECK CONSTRAINT [FK_SMSHistory_Ticket]
GO
ALTER TABLE [dbo].[subcategory]  WITH NOCHECK ADD  CONSTRAINT [FK_SubCategory_MainCategory] FOREIGN KEY([MainCategory])
REFERENCES [dbo].[maincategory] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[subcategory] CHECK CONSTRAINT [FK_SubCategory_MainCategory]
GO
ALTER TABLE [dbo].[ticket]  WITH CHECK ADD  CONSTRAINT [FK_ticket_status] FOREIGN KEY([CurrentStatus])
REFERENCES [dbo].[status] ([ID])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[ticket] CHECK CONSTRAINT [FK_ticket_status]
GO
ALTER TABLE [dbo].[ticket]  WITH CHECK ADD  CONSTRAINT [FK_ticket_tickettypes] FOREIGN KEY([TicketType])
REFERENCES [dbo].[tickettypes] ([TypeID])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[ticket] CHECK CONSTRAINT [FK_ticket_tickettypes]
GO
ALTER TABLE [dbo].[TicketAttachments]  WITH CHECK ADD  CONSTRAINT [FK_TicketAttachment_attachments] FOREIGN KEY([AttachmentID])
REFERENCES [dbo].[attachments] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[TicketAttachments] CHECK CONSTRAINT [FK_TicketAttachment_attachments]
GO
ALTER TABLE [dbo].[TicketAttachments]  WITH CHECK ADD  CONSTRAINT [FK_ticketAttchment_ticket] FOREIGN KEY([TicketID])
REFERENCES [dbo].[ticket] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[TicketAttachments] CHECK CONSTRAINT [FK_ticketAttchment_ticket]
GO
ALTER TABLE [dbo].[ticketdata]  WITH NOCHECK ADD  CONSTRAINT [FK_TicketData_TicketID] FOREIGN KEY([TicketID])
REFERENCES [dbo].[ticket] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ticketdata] CHECK CONSTRAINT [FK_TicketData_TicketID]
GO
ALTER TABLE [dbo].[ticketlock]  WITH NOCHECK ADD  CONSTRAINT [FK_Lock_Ticket] FOREIGN KEY([TicketID])
REFERENCES [dbo].[ticket] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ticketlock] CHECK CONSTRAINT [FK_Lock_Ticket]
GO
ALTER TABLE [dbo].[topic]  WITH NOCHECK ADD  CONSTRAINT [FK_Topic_SubCategory] FOREIGN KEY([SubCategory])
REFERENCES [dbo].[subcategory] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[topic] CHECK CONSTRAINT [FK_Topic_SubCategory]
GO
ALTER TABLE [dbo].[topicsla]  WITH CHECK ADD  CONSTRAINT [FK_TopicSLA_sla] FOREIGN KEY([SLAID])
REFERENCES [dbo].[sla] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[topicsla] CHECK CONSTRAINT [FK_TopicSLA_sla]
GO
ALTER TABLE [dbo].[topicsla]  WITH CHECK ADD  CONSTRAINT [FK_TopicSLA_topic] FOREIGN KEY([TopicID])
REFERENCES [dbo].[topic] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[topicsla] CHECK CONSTRAINT [FK_TopicSLA_topic]
GO
ALTER TABLE [dbo].[topicspermissions]  WITH CHECK ADD  CONSTRAINT [FK_TopicsPermissions_topic] FOREIGN KEY([topicId])
REFERENCES [dbo].[topic] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[topicspermissions] CHECK CONSTRAINT [FK_TopicsPermissions_topic]
GO
ALTER TABLE [dbo].[userroles]  WITH CHECK ADD  CONSTRAINT [FK_userspermissions_userspermissions] FOREIGN KEY([ID])
REFERENCES [dbo].[userroles] ([ID])
GO
ALTER TABLE [dbo].[userroles] CHECK CONSTRAINT [FK_userspermissions_userspermissions]
GO
ALTER TABLE [dbo].[usertopic]  WITH NOCHECK ADD  CONSTRAINT [FK_UsersTopic_Topic] FOREIGN KEY([TopicID])
REFERENCES [dbo].[topic] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[usertopic] CHECK CONSTRAINT [FK_UsersTopic_Topic]
GO
/****** Object:  StoredProcedure [dbo].[GenerateTopicGroupPermissionMapping]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:      <Author,,Name>
-- Create date: <Create Date,,>
-- Description:  <Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GenerateTopicGroupPermissionMapping] @topicID int,
@groupName varchar(50) NULL,
@GroupID int
AS
BEGIN
  -- SET NOCOUNT ON added to prevent extra result sets from
  -- interfering with SELECT statements.
  SET NOCOUNT ON;
  
  DECLARE @username AS varchar(50);
  DECLARE @userid AS int;
  DECLARE @UsersList AS TABLE (
    userID int,
    username varchar(50)
  );
  DECLARE @PermHolder AS TABLE (
    [TopicID] [int] NOT NULL,

    [InheritedFrom] [varchar](10) NULL,
    [CreationDate] [datetime] NULL,
    [CreatedBy] [varchar](50) NULL,
    [admin] [tinyint] NULL,
    [canCreate] [tinyint] NULL,
	 canChgDpt tinyint null,
	 canAssign tinyint null,
    [canReopen] [tinyint] NULL,
    [canRead] [tinyint] NULL,
    [canDelete] [tinyint] NULL,
    [canReply] [tinyint] NULL,
    [canClose] [tinyint] NULL,
    [canResolve] [tinyint] NULL,
    [canModify] [tinyint] NULL,
    [canRunReport] [tinyint] NULL,
    [canSubscribe] [tinyint] NULL
  );

  -- Insert statements for procedure here
  IF @groupname IS NULL or @groupName=''
  BEGIN
    SELECT
      @groupName = groupName,
      @GroupID = id
    FROM groups
    WHERE groups.ID = @GroupID;
  END


  IF @groupID IS NOT NULL
  BEGIN
    INSERT INTO @UsersList (userID,username)
      SELECT
        usergroups.userID,users.UserID
      FROM usergroups left join users on users.ID=usergroups.userID
      WHERE usergroups.groupID = @groupID;
  END

  INSERT INTO @PermHolder (TopicID,

  InheritedFrom,
  CreationDate,
  CreatedBy,
  [admin],
  canCreate,
  canChgDpt,
  canAssign,
  canReopen,
  canRead,
  canDelete,
  canReply,
  canClose,
  canResolve,
  canModify,
  canRunReport,
  canSubscribe)
    SELECT DISTINCT
      Perms.topicId,
      Perms.type,
      GETDATE(),
       Perms.CreatedBy,
      Perms.admin,
      Perms.canCreate,
Perms.canChgDpt,
Perms.canAssign,
Perms.canReopen,
Perms.canRead,
Perms.canDelete,
Perms.canReply,
Perms.canClose,
Perms.canResolve,
Perms.canModify,
Perms.canRunReport,
Perms.canSubscribe
    FROM (SELECT DISTINCT
      tp.*
    FROM dbo.topicspermissions tp
    WHERE tp.type = 'group'
    AND assigne = @GroupID) Perms
    WHERE Perms.ID IS NOT NULL
    AND Perms.assigne IS NOT NULL
    AND Perms.topicId = @topicID

	 
  BEGIN TRY
    -- delete existing values
	Begin Transaction
    DELETE FROM [generatedTopicPermissions]
    WHERE [generatedTopicPermissions].userID IN (SELECT
        userid
      FROM @UsersList)
      AND [generatedTopicPermissions].topicID = @topicID
      AND [generatedTopicPermissions].InheritedFrom = 'group' 
    -- Rollback the transaction if there were any errors

    DECLARE db_cursor CURSOR FOR
    SELECT
      username,
      userid
    FROM @UsersList;
	 
    OPEN db_cursor
    FETCH NEXT FROM db_cursor INTO @username, @userid
    WHILE @@FETCH_STATUS = 0
    BEGIN
	    
     INSERT INTO [dbo].[generatedTopicPermissions]   ([TopicID]
           ,[UserName]
           ,[UserID]
           ,[InheritedFrom]
           ,[CreationDate]
           ,[CreatedBy]
           ,[admin]
           ,[canCreate]
           ,[canReopen]
           ,[canRead]
           ,[canDelete]
           ,[canReply]
           ,[canClose]
           ,[canResolve]
           ,[canModify]
           ,[canRunReport]
           ,[canSubscribe]
           ,[canAssign]
           ,[canChgDpt])
      SELECT
        [TopicID]
           ,@username
           ,@userid
           ,[InheritedFrom]
           ,[CreationDate]
           ,[CreatedBy]
           ,[admin]
           ,[canCreate]
           ,[canReopen]
           ,[canRead]
           ,[canDelete]
           ,[canReply]
           ,[canClose]
           ,[canResolve]
           ,[canModify]
           ,[canRunReport]
           ,[canSubscribe]
           ,[canAssign]
           ,[canChgDpt]
      FROM @PermHolder;

		FETCH NEXT FROM db_cursor INTO @username, @userid;
    END
	commit;
	   CLOSE db_cursor
       DEALLOCATE db_cursor
	 
    END Try

	BEGIN CATCH

        SELECT ERROR_MESSAGE()

        IF @@TRANCOUNT>0
            ROLLBACK

	   CLOSE db_cursor
       DEALLOCATE db_cursor

    END CATCH

END
	
  



GO
/****** Object:  StoredProcedure [dbo].[GenerateTopicUserPermissionMapping]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:      <Author,,Name>
-- Create date: <Create Date,,>
-- Description:  <Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GenerateTopicUserPermissionMapping] @topicID int,
@username varchar(50) null,
@userid int
 

AS
BEGIN
  -- SET NOCOUNT ON added to prevent extra result sets from
  -- interfering with SELECT statements.
  SET NOCOUNT ON;
 
 declare @PermHolder AS TABLE
  (
  [TopicID] [int] NOT NULL,
  [UserName] [varchar](50) NULL,
  [UserID] [int] NULL,
  [InheritedFrom] [varchar](10) NULL,
  [CreationDate] [datetime] NULL,
  [CreatedBy] [varchar](50) NULL,
  [admin] [tinyint] NULL,
  [canCreate] [tinyint] NULL,
  canChgDpt tinyint null,
  canAssign tinyint null,
  [canReopen] [tinyint] NULL,
  [canRead] [tinyint] NULL,
  [canDelete] [tinyint] NULL,
  [canReply] [tinyint] NULL,
  [canClose] [tinyint] NULL,
  [canResolve] [tinyint] NULL,
  [canModify] [tinyint] NULL,
  [canRunReport] [tinyint] NULL,
  [canSubscribe] [tinyint] NULL);

  -- Insert statements for procedure here
  IF @username IS NOT NULL and @username <>''
  BEGIN
 
      SELECT
        @userid=id,@username=UserID
      FROM users
      WHERE users.UserID = @username;

  END
   ELSE IF @userid IS NOT NULL
  BEGIN
   
      SELECT
        @userid=id,@username=UserID
      FROM users
      WHERE users.ID = @userid;

  END

   
  INSERT INTO @PermHolder(TopicID,
  [UserName],
  UserID,
  InheritedFrom,
  CreationDate,
  CreatedBy,
  [admin],
  canCreate,
  canChgDpt,
  canAssign,
  canReopen,
  canRead,
  canDelete,
  canReply,
  canClose,
  canResolve,
  canModify,
  canRunReport,
  canSubscribe)
    SELECT DISTINCT
      Perms.topicId,
      @username,
      @userid,
      Perms.type,
      GETDATE(),
      Perms.CreatedBy,
      Perms.admin,
      Perms.canCreate,
Perms.canChgDpt,
Perms.canAssign,
Perms.canReopen,
Perms.canRead,
Perms.canDelete,
Perms.canReply,
Perms.canClose,
Perms.canResolve,
Perms.canModify,
Perms.canRunReport,
Perms.canSubscribe
    FROM (SELECT DISTINCT ID,
	Assigne,
	[topicId]
      ,[type]
      , admin,
       canCreate,
  canChgDpt,
  canAssign,
  canReopen,
  canRead,
  canDelete,
  canReply,
  canClose,
  canResolve,
  canModify,
  canRunReport,
  canSubscribe
      ,[CreatedBy]
      ,[CreationDate]
      ,[ModifiedBy]
      ,[ModificationDate]
    FROM dbo.topicspermissions tp
    WHERE tp.assigne=@userid
    AND tp.type = 'user') Perms
    WHERE Perms.ID IS NOT NULL and PErms.assigne is not null and Perms.topicId=@topicID


  BEGIN TRANSACTION
    -- delete existing values
    DELETE FROM [generatedTopicPermissions]
    WHERE [generatedTopicPermissions].userID=@userid and  [generatedTopicPermissions].topicID=@topicID
    -- Rollback the transaction if there were any errors
    IF @@ERROR <> 0
    BEGIN
      -- Rollback the transaction
      ROLLBACK
      -- Raise an error and return
      RAISERROR ('Error in deleting users in permissions.', 16, 1)
      RETURN
    END

    INSERT INTO [dbo].[generatedTopicPermissions]   ([TopicID]
           ,[UserName]
           ,[UserID]
           ,[InheritedFrom]
           ,[CreationDate]
           ,[CreatedBy]
           ,[admin]
           ,[canCreate]
           ,[canReopen]
           ,[canRead]
           ,[canDelete]
           ,[canReply]
           ,[canClose]
           ,[canResolve]
           ,[canModify]
           ,[canRunReport]
           ,[canSubscribe]
           ,[canAssign]
           ,[canChgDpt])
      SELECT
        [TopicID]
           ,[UserName]
           ,[UserID]
           ,[InheritedFrom]
           ,[CreationDate]
           ,[CreatedBy]
           ,[admin]
           ,[canCreate]
           ,[canReopen]
           ,[canRead]
           ,[canDelete]
           ,[canReply]
           ,[canClose]
           ,[canResolve]
           ,[canModify]
           ,[canRunReport]
           ,[canSubscribe]
           ,[canAssign]
           ,[canChgDpt]
      FROM @PermHolder;
    IF @@ERROR <> 0
    BEGIN
      -- Rollback the transaction
      ROLLBACK
      -- Raise an error and return
      RAISERROR ('Error in INSERTING users in permissions.', 16, 1)
      RETURN
    END

   COMMIT;

   select * from @PermHolder;

END


GO
/****** Object:  StoredProcedure [dbo].[GetEscalatedTickets]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[GetEscalatedTickets]

AS
BEGIN

  DECLARE @EscTickets TABLE (
    TicketID bigint NOT NULL,
	TopicSLA int not NULL
  )

  SET NOCOUNT ON;
  INSERT INTO @EscTickets
    SELECT DISTINCT
      ticket.id,
	  topicSla.ID
    FROM topicsla
    LEFT JOIN sla
      ON sla.ID = topicsla.SLAID
    LEFT JOIN ticket
      ON topicsla.TopicID = ticket.Topic
    WHERE (ticket.Deleted IS NULL
    OR ticket.Deleted = 0)
    AND ticket.CurrentStatus NOT IN (SELECT
      id
    FROM [status] s
    WHERE (s.EnableSLA = 0
    OR s.Enabled = 0))
    AND topicsla.CreationDate <= ticket.CreationDate
    AND topicsla.SLALevel =
                           CASE
                             WHEN ticket.lastSLA IS NULL THEN 1
                             ELSE (SELECT
                                 topicsla.SLALevel
                               FROM topicsla
                               WHERE ID = ticket.lastSLA)
                               + 1
                           END
    AND dbo.WorkTime(ticket.EscalationCalDate, GETDATE(), '08:00', '17:00') >= ((sla.Time) * 60)
    ORDER BY ticket.id, topicsla.ID

  DECLARE @totalRecord int;

  SELECT
    @totalRecord = COUNT(TicketID)
  FROM @EscTickets;

  IF (@totalRecord > 0)
  BEGIN
    DECLARE @TicketID bigint;
    DECLARE @TopicSLA int;
    
    DECLARE @ticketCursor AS CURSOR;

    SET @ticketCursor = CURSOR FOR
    SELECT
      TicketID,
      TopicSLA
    FROM @EscTickets

    OPEN @ticketCursor
    FETCH NEXT FROM @ticketCursor INTO @TICKETID, @TopicSLA

    WHILE @@FETCH_STATUS = 0
    BEGIN
	  Update Ticket set EscalationCalDate=GETDATE(),LastSLA=@TopicSLA where id=@TicketID;
      FETCH NEXT FROM @ticketCursor INTO @TICKETID, @TopicSLA 
    END
    CLOSE @ticketCursor
    DEALLOCATE @ticketCursor
  END
  ---
  SELECT * from @EscTickets;
END
GO
/****** Object:  StoredProcedure [dbo].[GetUserMainCats]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GetUserMainCats] 
	-- Add the parameters for the stored procedure here
	 @username varchar(50)
AS
BEGIN
 declare @userId int;
  select @userId=id from users where users.UserID=@username;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	Select distinct * from
(select distinct mc.*  from dbo.topicspermissions tp left join topic t on tp.topicId=t.ID left join subcategory s on s.ID=t.SubCategory left join maincategory mc on mc.ID=s.MainCategory    where t.Enabled=1 and mc.Enabled=1 and s.Enabled=1 and tp.assigne=@userId and tp.type='user' 
Union All
 select distinct mc.*  from dbo.topicspermissions tp left join topic t on tp.topicId=t.ID  left join subcategory s on s.ID=t.SubCategory left join maincategory mc on mc.ID=s.MainCategory     where t.Enabled=1 and mc.Enabled=1 and s.Enabled=1 and tp.type='group' and tp.assigne in   (select  groupID from usergroups where userID=@userId)
  )  topics
  where topics.ID  is not null
END
GO
/****** Object:  StoredProcedure [dbo].[GetUserPermissions]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
Create PROCEDURE [dbo].[GetUserPermissions] 
	-- Add the parameters for the stored procedure here
	 @username varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

declare @userId int

select @userId=id from users where users.UserID=@username;

Select distinct * from
(select distinct p.*  from dbo.permissions p right join [dbo].[rolepermissions] rp on p.id=rp.permissionID
 and rp.roleID in (SELECT   roleID
  FROM [userroles] left join users on users.ID=[userroles].userID where users.ID=@userId)
Union All
Select distinct p.*  from dbo.permissions p right join [dbo].[rolepermissions] rp on p.id=rp.permissionID
 and rp.roleID in (SELECT   roleID
  FROM  [dbo].[grouproles]  left join dbo.[groups] on dbo.[groups].ID=[grouproles].groupID where groups.Enabled=1 and  groups.id in (select  groupID from usergroups where userID=@userId))
  )  Perms
  where Perms.ID  is not null
END
GO
/****** Object:  StoredProcedure [dbo].[GetUserSubCats]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GetUserSubCats] 
	-- Add the parameters for the stored procedure here
	 @username varchar(50),
	 @mainCat int
AS
BEGIN
 declare @userId int;
  select @userId=id from users where users.UserID=@username;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	Select distinct * from
(select distinct s.*  from dbo.topicspermissions tp left join topic t on tp.topicId=t.ID left join subcategory s on s.ID=t.SubCategory     where s.MainCategory=@mainCat and t.Enabled=1 and s.Enabled=1 and tp.assigne=@userId and tp.type='user' 
Union All
 select distinct s.*  from dbo.topicspermissions tp left join topic t on tp.topicId=t.ID  left join subcategory s on s.ID=t.SubCategory     where s.MainCategory=@mainCat and t.Enabled=1 and   s.Enabled=1 and tp.type='group' and tp.assigne in   (select  groupID from usergroups where userID=@userId)
  )  topics
  where topics.ID  is not null
END
GO
/****** Object:  StoredProcedure [dbo].[GetUserTopicPermissions]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:      <Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
Create PROCEDURE [dbo].[GetUserTopicPermissions]
-- Add the parameters for the stored procedure here
@username varchar(50),
@topicID int
AS
BEGIN
  -- SET NOCOUNT ON added to prevent extra result sets from
  -- interfering with SELECT statements.
  SET NOCOUNT ON;
  declare @userId int;
  select @userId=id from users where users.UserID=@username;

  IF @topicID <> -1
  BEGIN
    SELECT DISTINCT
      *
    FROM (SELECT DISTINCT
      tp.*
    FROM dbo.topicspermissions tp
    WHERE tp.assigne = @userId
    AND tp.type = 'user'
    UNION ALL
    SELECT DISTINCT
      tp.*
    FROM dbo.topicspermissions tp
    WHERE tp.type = 'group'
    AND tp.assigne IN (SELECT
      groupID
    FROM usergroups
    WHERE userID = @userId)) Perms
    WHERE Perms.ID IS NOT NULL
  END
  ELSE
  BEGIN
    SELECT DISTINCT
      *
    FROM (SELECT DISTINCT
      tp.*
    FROM dbo.topicspermissions tp
    WHERE tp.topicId = @topicID
    AND tp.assigne = @userId
    AND tp.type = 'user'
    UNION ALL
    SELECT DISTINCT
      tp.*
    FROM dbo.topicspermissions tp
    WHERE tp.type = 'group'
    AND tp.topicId = @topicID
    AND tp.assigne IN (SELECT
      groupID
    FROM usergroups
    WHERE userID = @userId)) Perms
    WHERE Perms.ID IS NOT NULL
  END

END
GO
/****** Object:  StoredProcedure [dbo].[GetUserTopics]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GetUserTopics] 
	-- Add the parameters for the stored procedure here
	 @username varchar(50)
AS

BEGIN
declare  @userId int
 select @userId=id from users where users.UserID=@username;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	Select distinct * from
(select distinct t.*  from dbo.topicspermissions tp left join topic t on tp.topicId=t.ID and t.Enabled=1 where tp.assigne=@userId and tp.type='user' 
Union All
 select distinct t.*  from dbo.topicspermissions tp left join topic t on tp.topicId=t.ID and t.Enabled=1  where tp.type='group' and tp.assigne in   (select  groupID from usergroups where userID=@userId)
  )  topics
  where topics.ID  is not null
END
GO
/****** Object:  StoredProcedure [dbo].[GetUserTopicsBySubCat]    Script Date: 3/14/2019 9:53:07 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GetUserTopicsBySubCat] 
	-- Add the parameters for the stored procedure here
	 @username varchar(50),
	 @subCat int
AS
BEGIN
	declare @userId int;
	select @userId=id from users where users.UserID=@username;
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	Select distinct * from
(select distinct t.*  from dbo.topicspermissions tp left join topic t on tp.topicId=t.ID      where t.SubCategory=@subCat and t.Enabled=1 and    tp.assigne=@userId and tp.type='user' 
Union All
 select distinct t.*  from dbo.topicspermissions tp left join topic t on tp.topicId=t.ID      where  t.SubCategory=@subCat and t.Enabled=1 and    tp.type='group' and tp.assigne in   (select  groupID from usergroups where userID=@userId)
  )  topics
  where topics.ID  is not null
END
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.usersassignedtickets' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'assignedtickets'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.attachments' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'attachments'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.calendar' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'calendar'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.emailhistory' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'emailhistory'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.emailmessage' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'emailmessage'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.emailtemplates' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'emailtemplates'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.escalationhistory' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'escalationhistory'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.globalconfiguration' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'globalconfiguration'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.group' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'groups'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.ticketholdreason' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'holdreason'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.holidays' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'holidays'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.logindetails' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'logindetails'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.maincategory' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'maincategory'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.maincatholidays' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'maincatholidays'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.sla' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sla'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.userssla' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'slausers'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.smshistory' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'smsHistory'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.smstemplate' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'smsTemplate'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.sourcechannel' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'sourceChannel'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.status' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'status'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.subcategory' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'subcategory'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.ticket' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ticket'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.ticketactions' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ticketactions'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.ticketdata' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ticketdata'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.ticketlock' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'ticketlock'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.type' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'tickettypes'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.topic' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'topic'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_SSMA_SOURCE', @value=N'ticketing.users' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'users'
GO
USE [master]
GO
ALTER DATABASE [BAJTicketingNew] SET  READ_WRITE 
GO
