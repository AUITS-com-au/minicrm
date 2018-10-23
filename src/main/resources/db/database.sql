USE [master]
GO
/****** Object:  Database [BAJTicketingNew]    Script Date: 10/23/2018 8:24:52 AM ******/
CREATE DATABASE [BAJTicketingNew]
CONTAINMENT = NONE
ON PRIMARY
  (NAME = N'BAJTicketingNew', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.MSSQLSERVER\MSSQL\DATA\BAJTicketingNew.mdf', SIZE = 8192 KB, MAXSIZE = UNLIMITED, FILEGROWTH = 65536 KB)
LOG ON
  (NAME = N'BAJTicketingNew_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL13.MSSQLSERVER\MSSQL\DATA\BAJTicketingNew_log.ldf', SIZE = 8192 KB, MAXSIZE = 2048 GB, FILEGROWTH = 65536 KB)
GO
ALTER DATABASE [BAJTicketingNew] SET COMPATIBILITY_LEVEL = 130
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
  BEGIN
    EXEC [BAJTicketingNew].[dbo].[sp_fulltext_database] @action = 'enable'
  END
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
ALTER DATABASE [BAJTicketingNew] SET CURSOR_DEFAULT GLOBAL
GO
ALTER DATABASE [BAJTicketingNew] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [BAJTicketingNew] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [BAJTicketingNew] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [BAJTicketingNew] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [BAJTicketingNew] SET DISABLE_BROKER
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
ALTER DATABASE [BAJTicketingNew] SET MULTI_USER
GO
ALTER DATABASE [BAJTicketingNew] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [BAJTicketingNew] SET DB_CHAINING OFF
GO
ALTER DATABASE [BAJTicketingNew] SET FILESTREAM (
  NON_TRANSACTED_ACCESS = OFF )
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
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO
ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO
ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO
USE [BAJTicketingNew]
GO
USE [BAJTicketingNew]
GO
/****** Object:  Sequence [dbo].[ticketNumber]    Script Date: 10/23/2018 8:24:53 AM ******/
CREATE SEQUENCE [dbo].[ticketNumber]
AS [BIGINT]
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 10000
CYCLE
CACHE
GO
/****** Object:  Table [dbo].[assignedtickets]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[assignedtickets] (
  [ID]               [BIGINT] IDENTITY (16, 1) NOT NULL,
  [ticketID]         [BIGINT]                  NULL,
  [userID]           [NVARCHAR](50)            NULL,
  [Enabled]          [INT]                     NULL,
  [CreationDate]     [DATETIME]                NOT NULL,
  [CreatedBy]        [NVARCHAR](50)            NULL,
  [ModifiedBy]       [NVARCHAR](50)            NULL,
  [ModificationDate] [VARCHAR](8000)           NULL,
  CONSTRAINT [PK_usersassignedtickets_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[attachments]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[attachments] (
  [ID]               [BIGINT] IDENTITY (59, 1) NOT NULL,
  [FileName]         [NVARCHAR](500)           NOT NULL,
  [FileType]         [NVARCHAR](500)           NULL,
  [Hash]             [NVARCHAR](50)            NULL,
  [RAWContent]       [VARBINARY](MAX)          NULL,
  [FilePath]         [NVARCHAR](500)           NULL,
  [CreatedBy]        [NVARCHAR](50)            NOT NULL,
  [CreationDate]     [DATETIME]                NOT NULL,
  [ModifiedBy]       [NVARCHAR](50)            NULL,
  [ModificationDate] [DATETIME]                NULL,
  [FileDesc]         [NVARCHAR](1000)          NULL,
  CONSTRAINT [PK_attachments_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[calendar]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[calendar] (
  [dt]          [DATETIME]      NOT NULL,
  [isWeekday]   [SMALLINT]      NULL,
  [isHoliday]   [SMALLINT]      NULL,
  [Y]           [SMALLINT]      NULL,
  [FY]          [SMALLINT]      NULL,
  [Q]           [TINYINT]       NULL,
  [M]           [TINYINT]       NULL,
  [D]           [TINYINT]       NULL,
  [DW]          [TINYINT]       NULL,
  [monthname]   [NVARCHAR](9)   NULL,
  [dayname]     [NVARCHAR](9)   NULL,
  [W]           [TINYINT]       NULL,
  [Description] [NVARCHAR](150) NULL,
  CONSTRAINT [PK_calendar_dt] PRIMARY KEY CLUSTERED
    (
      [dt] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[countrycode]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[countrycode] (
  [ID]                [INT]           NOT NULL,
  [CountryName]       [NVARCHAR](255) NULL,
  [ISO2]              [NVARCHAR](255) NULL,
  [ISO3]              [NVARCHAR](255) NULL,
  [TopLevelDomain]    [NVARCHAR](255) NULL,
  [FIPS]              [NVARCHAR](255) NULL,
  [ISONumeric]        [FLOAT]         NULL,
  [GeoNameID]         [FLOAT]         NULL,
  [E164]              [FLOAT]         NULL,
  [PhoneCode]         [NVARCHAR](255) NULL,
  [Continent]         [NVARCHAR](255) NULL,
  [Capital]           [NVARCHAR](255) NULL,
  [TimeZoneinCapital] [NVARCHAR](255) NULL,
  [Currency]          [NVARCHAR](255) NULL,
  [LanguageCodes]     [NVARCHAR](255) NULL,
  [Languages]         [NVARCHAR](255) NULL,
  [AreaKM2]           [FLOAT]         NULL,
  [InternetHosts]     [FLOAT]         NULL,
  [InternetUsers]     [FLOAT]         NULL,
  [Mobile]            [FLOAT]         NULL,
  [Landline]          [FLOAT]         NULL,
  [GDP]               [FLOAT]         NULL,
  [MobileLength]      [INT]           NULL,
  [Enabled]           [TINYINT]       NULL,
  CONSTRAINT [PK_countrycode] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customeraccounts]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customeraccounts] (
  [ID]               [BIGINT] IDENTITY (1, 1) NOT NULL,
  [CreatedBy]        [NVARCHAR](50)           NULL,
  [ModifiedBy]       [NVARCHAR](50)           NULL,
  [CreationDate]     [DATETIME]               NULL,
  [ModificationDate] [DATETIME]               NULL,
  [CustomerNameAR]   [NVARCHAR](250)          NULL,
  [CustomerNameEn]   [NVARCHAR](250)          NULL,
  [Email]            [VARCHAR](50)            NULL,
  [Mobile]           [VARCHAR](15)            NULL,
  [Nin]              [VARCHAR](15)            NULL,
  [Segment]          [NVARCHAR](50)           NULL,
  CONSTRAINT [PK_CustomerAccounts] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[emailhistory]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[emailhistory] (
  [ID]           [BIGINT] IDENTITY (702, 1) NOT NULL,
  [TicketID]     [BIGINT]                   NOT NULL,
  [EmailID]      [NVARCHAR](250)            NOT NULL,
  [CopyTo]       [NVARCHAR](MAX)            NULL,
  [BCopyTo]      [NVARCHAR](MAX)            NULL,
  [CreationDate] [DATETIME]                 NULL,
  [SendDate]     [DATETIME]                 NULL,
  [SendingOn]    [NVARCHAR](120)            NOT NULL,
  [Status]       [INT]                      NULL,
  [Type]         [NVARCHAR](50)             NULL,
  [Result]       [NVARCHAR](MAX)            NULL,
  [EmailMessage] [BIGINT]                   NULL,
  [ReadStatus]   [TINYINT]                  NULL,
  [DeleteStatus] [TINYINT]                  NULL,
  CONSTRAINT [PK_emailhistory_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[emailmessage]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[emailmessage] (
  [Id]           [BIGINT] IDENTITY (116, 1) NOT NULL,
  [EmailTitle]   [NVARCHAR](500)            NOT NULL,
  [EmailMessage] [NVARCHAR](MAX)            NOT NULL,
  CONSTRAINT [PK_emailmessage_Id] PRIMARY KEY CLUSTERED
    (
      [Id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[emailtemplates]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[emailtemplates] (
  [TemplateID]       [INT] IDENTITY (39, 1) NOT NULL,
  [TemplateName]     [NVARCHAR](150)        NULL,
  [TemplateData]     [NVARCHAR](MAX)        NULL,
  [enabled]          [SMALLINT]             NULL,
  [CreatedBy]        [NVARCHAR](50)         NULL,
  [CreationDate]     [DATETIME]             NULL,
  [ModificationDate] [DATETIME]             NULL,
  [ModifiedBy]       [NVARCHAR](50)         NULL,
  CONSTRAINT [PK_emailtemplates_TemplateID] PRIMARY KEY CLUSTERED
    (
      [TemplateID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[escalationhistory]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[escalationhistory] (
  [ID]          [BIGINT] IDENTITY (236, 1) NOT NULL,
  [TicketID]    [BIGINT]                   NOT NULL,
  [SLA]         [INT]                      NOT NULL,
  [ESCLevel]    [INT]                      NOT NULL,
  [ESCDateTime] [DATETIME]                 NOT NULL,
  [EscUsers]    [NVARCHAR](MAX)            NULL,
  [EscEmails]   [NVARCHAR](MAX)            NULL,
  CONSTRAINT [PK_escalationhistory_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[globalconfiguration]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[globalconfiguration] (
  [Property] [NVARCHAR](50) NOT NULL,
  [Value]    [NTEXT]        NOT NULL,
  [type]     [NVARCHAR](50) NULL,
  CONSTRAINT [PK_globalconfiguration_Property] PRIMARY KEY CLUSTERED
    (
      [Property] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[grouproles]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[grouproles] (
  [ID]           [INT] IDENTITY (1, 1) NOT NULL,
  [groupID]      [INT]                 NOT NULL,
  [roleID]       [INT]                 NOT NULL,
  [CreationDate] [DATETIME]            NOT NULL,
  [CreatedBy]    [NVARCHAR](50)        NOT NULL,
  CONSTRAINT [PK_groupspermissions] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[groups]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[groups] (
  [ID]               [INT] IDENTITY (1, 1) NOT NULL,
  [GroupName]        [NVARCHAR](50)        NOT NULL,
  [CreationDate]     [DATETIME]            NOT NULL,
  [ModificationDate] [DATETIME]            NULL,
  [CreatedBy]        [NVARCHAR](50)        NOT NULL,
  [ModifiedBy]       [NVARCHAR](50)        NULL,
  [Enabled]          [TINYINT]             NOT NULL,
  CONSTRAINT [PK_group_GroupNAME] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[holdreason]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[holdreason] (
  [ReasonID]         [INT] IDENTITY (3, 1) NOT NULL,
  [SortOrder]        [INT]                 NULL,
  [ArabicLabel]      [NVARCHAR](150)       NULL,
  [EnglishLabel]     [NVARCHAR](150)       NULL,
  [CreationDate]     [DATETIME]            NULL,
  [ModificationDate] [DATETIME]            NULL,
  [CreatedBy]        [NVARCHAR](50)        NULL,
  [ModifiedBy]       [NVARCHAR](50)        NULL,
  [HoldTime]         [INT]                 NULL,
  [Flexible]         [TINYINT]             NULL,
  CONSTRAINT [PK_ticketholdreason_ReasonID] PRIMARY KEY CLUSTERED
    (
      [ReasonID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[holidays]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[holidays] (
  [HolidayID]        [INT] IDENTITY (1, 1) NOT NULL,
  [HolidayName]      [NVARCHAR](250)       NULL,
  [StartDate]        [DATE]                NOT NULL,
  [EndData]          [DATE]                NOT NULL,
  [Enabled]          [SMALLINT]            NOT NULL,
  [CreatedBy]        [NVARCHAR](50)        NOT NULL,
  [CreationDate]     [DATETIME]            NOT NULL,
  [ModifiedBy]       [NVARCHAR](50)        NULL,
  [ModificationDate] [DATETIME]            NULL,
  CONSTRAINT [PK_holidays_HolidayID] PRIMARY KEY CLUSTERED
    (
      [HolidayID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY],
  CONSTRAINT [holidays$IX_Holidays] UNIQUE NONCLUSTERED
    (
      [Enabled] ASC,
      [StartDate] ASC,
      [EndData] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[logindetails]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[logindetails] (
  [ID]             [BIGINT] IDENTITY (1778, 1) NOT NULL,
  [UserID]         [NVARCHAR](50)              NOT NULL,
  [DateTime]       [DATETIME]                  NOT NULL,
  [Result]         [SMALLINT]                  NOT NULL,
  [IPAddress]      [VARCHAR](20)               NULL,
  [LogoutDateTime] [DATETIME]                  NULL,
  [Token]          [VARCHAR](500)              NULL,
  [LogoutReason]   [VARCHAR](100)              NULL,
  CONSTRAINT [PK_logindetails_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[maincategory]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[maincategory] (
  [ID]               [INT] IDENTITY (26, 1) NOT NULL,
  [ArabicLabel]      [NVARCHAR](150)        NOT NULL,
  [EnglishLabel]     [NVARCHAR](150)        NOT NULL,
  [CreatedBy]        [NVARCHAR](50)         NULL,
  [ModifiedBy]       [NVARCHAR](50)         NULL,
  [CreationDate]     [DATETIME]             NULL,
  [ModificationDate] [DATETIME]             NULL,
  [Enabled]          [TINYINT]              NULL,
  CONSTRAINT [PK_maincategory_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[maincatholidays]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[maincatholidays] (
  [Id]           [INT] IDENTITY (1, 1) NOT NULL,
  [MainCategory] [INT]                 NOT NULL,
  [HolidayID]    [INT]                 NOT NULL,
  CONSTRAINT [PK_maincatholidays_Id] PRIMARY KEY CLUSTERED
    (
      [Id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[notifications]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notifications] (
  [ID]           [BIGINT] IDENTITY (1, 1) NOT NULL,
  [TicketID]     [BIGINT]                 NOT NULL,
  [DataID]       [BIGINT]                 NULL,
  [Action]       [INT]                    NULL,
  [CreationDate] [DATETIME]               NULL,
  [CreatedBy]    [NVARCHAR](50)           NULL,
  [Status]       [TINYINT]                NULL,
  CONSTRAINT [PK_NotificationMessages] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[permissions]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[permissions] (
  [ID]          [INT] IDENTITY (1, 1) NOT NULL,
  [Permission]  [VARCHAR](50)         NOT NULL,
  [ModuleName]  [VARCHAR](50)         NULL,
  [GroupName]   [VARCHAR](50)         NULL,
  [Description] [NVARCHAR](150)       NULL,
  CONSTRAINT [PK_Permissions] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rolepermissions]    Script Date: 10/23/2018 8:24:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rolepermissions] (
  [ID]           [INT] IDENTITY (1, 1) NOT NULL,
  [roleID]       [INT]                 NOT NULL,
  [permissionID] [INT]                 NOT NULL,
  [CreationDate] [DATETIME]            NOT NULL,
  [CreatedBy]    [NVARCHAR](50)        NOT NULL,
  CONSTRAINT [PK_rolepermissions] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles] (
  [ID]               [INT] IDENTITY (1, 1) NOT NULL,
  [Role]             [VARCHAR](50)         NOT NULL,
  [CreatedBy]        [VARCHAR](255)        NULL,
  [CreationDate]     [DATETIME2](7)        NULL,
  [ModificationDate] [DATETIME2](7)        NULL,
  [ModifiedBy]       [VARCHAR](255)        NULL,
  CONSTRAINT [PK_roles] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sla]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sla] (
  [ID]               [INT] IDENTITY (5000, 1) NOT NULL,
  [Time]             [INT]                    NOT NULL,
  [CreationDate]     [DATETIME]               NULL,
  [CreatedBy]        [NVARCHAR](50)           NULL,
  [ModifiedBy]       [NVARCHAR](50)           NULL,
  [ModificationDate] [DATETIME]               NULL,
  [SLAName]          [NVARCHAR](50)           NULL,
  [CatID]            [INT]                    NULL,
  CONSTRAINT [PK_sla_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[slausers]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[slausers] (
  [id]               [BIGINT] IDENTITY (72, 1) NOT NULL,
  [UserId]           [NVARCHAR](50)            NULL,
  [Sla]              [INT]                     NOT NULL,
  [CreatedBy]        [NVARCHAR](45)            NOT NULL,
  [CreationDate]     [DATETIME]                NOT NULL,
  [ModifiedBy]       [NVARCHAR](45)            NULL,
  [ModificationDate] [DATETIME]                NULL,
  [Enabled]          [TINYINT]                 NOT NULL,
  [Emails]           [NVARCHAR](MAX)           NULL,
  [MobileNumbers]    [NVARCHAR](MAX)           NULL,
  CONSTRAINT [PK_userssla_id] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[smsHistory]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[smsHistory] (
  [ID]           [BIGINT] IDENTITY (11, 1) NOT NULL,
  [TicketID]     [BIGINT]                  NOT NULL,
  [Mobile]       [NVARCHAR](20)            NOT NULL,
  [CreationDate] [DATETIME]                NOT NULL,
  [Message]      [NVARCHAR](MAX)           NOT NULL,
  [TemplateID]   [INT]                     NULL,
  [Status]       [INT]                     NOT NULL,
  [SendDate]     [DATETIME]                NULL,
  [SendingON]    [NVARCHAR](50)            NULL,
  [ErrorCode]    [NVARCHAR](MAX)           NULL,
  [BackEndID]    [NVARCHAR](50)            NULL,
  CONSTRAINT [PK_smshistory_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[smsTemplate]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[smsTemplate] (
  [ID]               [INT] IDENTITY (44, 1) NOT NULL,
  [Enabled]          [TINYINT]              NULL,
  [TemplateName]     [NVARCHAR](50)         NOT NULL,
  [CreationDate]     [DATETIME]             NULL,
  [CreatedBy]        [NVARCHAR](50)         NULL,
  [ModificationDate] [DATETIME]             NULL,
  [ModifiedBy]       [NVARCHAR](50)         NULL,
  [TemplateText]     [NVARCHAR](700)        NULL,
  CONSTRAINT [PK_smstemplate_TemplateID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sourceChannel]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sourceChannel] (
  [ChannelID]        [INT] IDENTITY (3, 1) NOT NULL,
  [ArabicLabel]      [NVARCHAR](150)       NOT NULL,
  [EnglishLabel]     [NVARCHAR](150)       NOT NULL,
  [CreatedBy]        [NVARCHAR](50)        NULL,
  [ModifiedBy]       [NVARCHAR](50)        NULL,
  [CreationDate]     [DATETIME]            NULL,
  [ModificationDate] [DATETIME]            NULL,
  [Enabled]          [TINYINT]             NULL,
  CONSTRAINT [PK_sourcechannel_ChannelID] PRIMARY KEY CLUSTERED
    (
      [ChannelID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[status]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[status] (
  [ID]                  [INT]           NOT NULL,
  [ListOrder]           [INT]           NULL,
  [Enabled]             [TINYINT]       NULL,
  [EnableSLA]           [TINYINT]       NULL,
  [DisplayOnTicketEdit] [TINYINT]       NULL,
  [ArabicLabel]         [NVARCHAR](150) NOT NULL,
  [EnglishLabel]        [NVARCHAR](150) NOT NULL,
  [CreatedBy]           [NVARCHAR](50)  NULL,
  [ModifiedBy]          [NVARCHAR](50)  NULL,
  [CreationDate]        [DATETIME]      NULL,
  [ModificationDate]    [DATETIME]      NULL,
  CONSTRAINT [PK_status_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[subcategory]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[subcategory] (
  [ID]               [INT] IDENTITY (45, 1) NOT NULL,
  [MainCategory]     [INT]                  NOT NULL,
  [ArabicLabel]      [NVARCHAR](150)        NOT NULL,
  [EnglishLabel]     [NVARCHAR](150)        NOT NULL,
  [CreatedBy]        [NVARCHAR](50)         NULL,
  [ModifiedBy]       [NVARCHAR](50)         NULL,
  [CreationDate]     [DATETIME]             NULL,
  [ModificationDate] [DATETIME]             NULL,
  [Enabled]          [TINYINT]              NULL,
  CONSTRAINT [PK_subcategory_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[subscriptions]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[subscriptions] (
  [ID]               [BIGINT] IDENTITY (1, 1) NOT NULL,
  [Topic]            [INT]                    NULL,
  [SubCategory]      [INT]                    NULL,
  [MainCat]          [INT]                    NULL,
  [TicketID]         [BIGINT]                 NULL,
  [CreationDate]     [DATETIME]               NULL,
  [ModificationDate] [DATETIME]               NULL,
  [CreatedBy]        [NVARCHAR](50)           NULL,
  [ModifiedBy]       [NVARCHAR](50)           NULL,
  [userID]           [NVARCHAR](50)           NULL,
  [subStatus]        [TINYINT]                NULL,
  [OnCreate]         [TINYINT]                NULL,
  [OnUpdate]         [TINYINT]                NULL,
  [OnClose]          [TINYINT]                NULL,
  [EmailID]          [NVARCHAR](250)          NULL,
  CONSTRAINT [PK_Subscriptions] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticket]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticket] (
  [ID]                [BIGINT]        NOT NULL,
  [Topic]             [INT]           NULL,
  [OriginalTopic]     [INT]           NULL,
  [CurrentStatus]     [INT]           NULL,
  [CreatedBy]         [NVARCHAR](50)  NOT NULL,
  [ModifiedBy]        [NVARCHAR](50)  NULL,
  [CreationDate]      [DATETIME]      NOT NULL,
  [ModificationDate]  [DATETIME]      NULL,
  [CrossedMainSLA]    [TINYINT]       NULL,
  [CustomerAccount]   [BIGINT]        NULL,
  [SourceChannel]     [INT]           NULL,
  [Subject]           [NVARCHAR](300) NULL,
  [TicketType]        [INT]           NULL,
  [Details]           [NVARCHAR](MAX) NULL,
  [AssignedTo]        [NVARCHAR](50)  NULL,
  [EscalationCalDate] [DATETIME]      NULL,
  [LastSLA]           [INT]           NULL,
  [Language]          [INT]           NULL,
  [Priority]          [INT]           NULL,
  [LastTicketData]    [BIGINT]        NULL,
  [Solved]            [TINYINT]       NULL,
  [Closed]            [TINYINT]       NULL,
  [Deleted]           [TINYINT]       NULL,
  CONSTRAINT [PK_ticket_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticketactions]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticketactions] (
  [ActionID]         [INT] IDENTITY (12, 1) NOT NULL,
  [ArabicLabel]      [NVARCHAR](150)        NULL,
  [EnglishLabel]     [NVARCHAR](150)        NULL,
  [Enabled]          [TINYINT]              NULL,
  [CreatedBy]        [NVARCHAR](50)         NULL,
  [ModifiedBy]       [NVARCHAR](50)         NULL,
  [CreationDate]     [DATETIME]             NULL,
  [ModificationDate] [DATETIME]             NULL,
  [SetStatusTo]      [INT]                  NULL,
  CONSTRAINT [PK_ticketactions_ActionID] PRIMARY KEY CLUSTERED
    (
      [ActionID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticketdata]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticketdata] (
  [ID]               [BIGINT] IDENTITY (58, 1) NOT NULL,
  [TicketID]         [BIGINT]                  NOT NULL,
  [ActionID]         [INT]                     NULL,
  [Title]            [NVARCHAR](400)           NULL,
  [TicketData]       [NVARCHAR](MAX)           NULL,
  [CreatedBy]        [NVARCHAR](50)            NULL,
  [CreationDate]     [DATETIME]                NULL,
  [ModifiedBy]       [NVARCHAR](50)            NULL,
  [ModificationDate] [DATETIME]                NULL,
  [Notes]            [NVARCHAR](MAX)           NULL,
  [Hidden]           [TINYINT]                 NULL,
  [OldStatus]        [INT]                     NULL,
  [NewStatus]        [INT]                     NULL,
  [OldTopic]         [INT]                     NULL,
  [NewTopic]         [INT]                     NULL,
  [NotificationID]   [BIGINT]                  NULL,
  [Notify]           [TINYINT]                 NULL,
  [NotfiyStatus]     [INT]                     NULL,
  CONSTRAINT [PK_ticketdata_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticketExtData]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticketExtData] (
  [ID]              [BIGINT] IDENTITY (1, 1) NOT NULL,
  [TicketID]        [BIGINT]                 NOT NULL,
  [BranchName]      [NVARCHAR](500)          NULL,
  [TransactionCode] [NVARCHAR](50)           NULL,
  [TransactionDesc] [NVARCHAR](MAX)          NULL,
  [TransactionType] [NVARCHAR](50)           NULL,
  [CustomerAccount] [NVARCHAR](50)           NULL,
  [Amount]          [NVARCHAR](20)           NULL,
  [TraxDateTime]    [DATETIME]               NULL,
  [ExtDate1]        [DATETIME]               NULL,
  [ExtDate2]        [DATETIME]               NULL,
  [ExtField1]       [NVARCHAR](250)          NULL,
  [ExtField2]       [NVARCHAR](250)          NULL,
  [ExtField3]       [NVARCHAR](250)          NULL,
  [ExtField4]       [NVARCHAR](250)          NULL,
  [ExtField5]       [NVARCHAR](250)          NULL,
  [ExtField6]       [NVARCHAR](250)          NULL,
  [ExtField7]       [NVARCHAR](250)          NULL,
  [ExtField8]       [NVARCHAR](250)          NULL,
  [ExtField9]       [NVARCHAR](250)          NULL,
  [ExtField10]      [NVARCHAR](250)          NULL,
  [ExtField11]      [NVARCHAR](250)          NULL,
  [ExtField12]      [NVARCHAR](250)          NULL,
  [ExtField13]      [NVARCHAR](250)          NULL,
  [ExtField14]      [NVARCHAR](250)          NULL,
  [ExtField15]      [NVARCHAR](250)          NULL,
  [ExtField16]      [NVARCHAR](250)          NULL,
  [ExtField17]      [NVARCHAR](250)          NULL,
  [ExtField18]      [NVARCHAR](250)          NULL,
  [ExtField19]      [NVARCHAR](250)          NULL,
  [ExtField20]      [NVARCHAR](250)          NULL,
  CONSTRAINT [PK_ticketExtData] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[ticketlock]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ticketlock] (
  [LockID]    [BIGINT] IDENTITY (113, 1) NOT NULL,
  [TicketID]  [BIGINT]                   NOT NULL,
  [UserID]    [NVARCHAR](50)             NOT NULL,
  [DateTime]  [DATETIME]                 NOT NULL,
  [ExpiresOn] [DATETIME]                 NOT NULL,
  [Expired]   [TINYINT]                  NULL,
  CONSTRAINT [PK_ticketlock_LockID] PRIMARY KEY CLUSTERED
    (
      [LockID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tickettypes]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tickettypes] (
  [TypeID]           [VARCHAR](50)   NOT NULL,
  [CreatedBy]        [NVARCHAR](50)  NULL,
  [CreationDate]     [DATETIME]      NULL,
  [ModifiedBy]       [NVARCHAR](50)  NULL,
  [ModificationDate] [DATETIME]      NULL,
  [ArabicLabel]      [NVARCHAR](150) NOT NULL,
  [EnglishLabel]     [NVARCHAR](150) NOT NULL,
  [Enabled]          [TINYINT]       NULL,
  CONSTRAINT [PK_type_TypeID] PRIMARY KEY CLUSTERED
    (
      [TypeID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[topic]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[topic] (
  [ID]               [INT] IDENTITY (1, 1) NOT NULL,
  [SubCategory]      [INT]                 NOT NULL,
  [ArabicLabel]      [NVARCHAR](150)       NOT NULL,
  [EnglishLabel]     [NVARCHAR](150)       NOT NULL,
  [CreationDate]     [DATETIME]            NULL,
  [ModificationDate] [DATETIME]            NULL,
  [CreatedBy]        [NVARCHAR](50)        NULL,
  [ModifiedBy]       [NVARCHAR](50)        NULL,
  [Configuration]    [NVARCHAR](MAX)       NOT NULL,
  [Enabled]          [TINYINT]             NULL,
  [Principals]       [NVARCHAR](MAX)       NULL,
  CONSTRAINT [PK_topic_ID] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[topicsla]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[topicsla] (
  [ID]               [INT] IDENTITY (1, 1) NOT NULL,
  [TopicID]          [INT]                 NOT NULL,
  [SLAID]            [INT]                 NOT NULL,
  [SLALevel]         [INT]                 NOT NULL,
  [CreationDate]     [DATETIME]            NULL,
  [CreatedBy]        [NVARCHAR](50)        NULL,
  [ModifiedBy]       [NVARCHAR](50)        NULL,
  [ModificationDate] [DATETIME]            NULL,
  [SLAImpl]          [VARCHAR](500)        NULL,
  CONSTRAINT [PK_TopicSLA_1] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[topicspermissions]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[topicspermissions] (
  [id]               [BIGINT] IDENTITY (1, 1) NOT NULL,
  [assigne]          [INT]                    NOT NULL,
  [type]             [VARCHAR](10)            NOT NULL,
  [topicId]          [INT]                    NOT NULL,
  [admin]            [TINYINT]                NULL,
  [canCreate]        [TINYINT]                NULL,
  [canReopen]        [TINYINT]                NULL,
  [canRead]          [TINYINT]                NULL,
  [canDelete]        [TINYINT]                NULL,
  [canReply]         [TINYINT]                NULL,
  [canClose]         [TINYINT]                NULL,
  [canResolve]       [TINYINT]                NULL,
  [canModify]        [TINYINT]                NULL,
  [canRunReport]     [TINYINT]                NULL,
  [canSubscribe]     [TINYINT]                NULL,
  [CreatedBy]        [NVARCHAR](50)           NULL,
  [CreationDate]     [DATETIME]               NULL,
  [ModifiedBy]       [NVARCHAR](50)           NULL,
  [ModificationDate] [DATETIME]               NULL,
  CONSTRAINT [PK_TopicsPermissions] PRIMARY KEY CLUSTERED
    (
      [id] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usergroups]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usergroups] (
  [ID]           [INT] IDENTITY (1, 1) NOT NULL,
  [CreatedBy]    [NVARCHAR](50)        NULL,
  [CreationDate] [DATETIME]            NULL,
  [groupID]      [INT]                 NOT NULL,
  [userID]       [INT]                 NOT NULL,
  CONSTRAINT [PK__usergrou__3214EC27C36D7FE9] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userpreferences]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userpreferences] (
  [ID]                   [BIGINT] IDENTITY (1, 1) NOT NULL,
  [UserID]               [NVARCHAR](50)           NOT NULL,
  [CreationDate]         [DATETIME]               NOT NULL,
  [CreatedBy]            [NVARCHAR](50)           NULL,
  [ModifiedBy]           [NVARCHAR](50)           NULL,
  [ModificationDate]     [VARCHAR](8000)          NULL,
  [EmailsNotifications]  [TINYINT]                NOT NULL,
  [Language]             [NCHAR](20)              NULL,
  [Avatar]               [IMAGE]                  NULL,
  [SLAEmails]            [TINYINT]                NULL,
  [TicketCreationEmails] [TINYINT]                NULL,
  [TicketEditEmails]     [TINYINT]                NULL,
  [TicketAssignEmails]   [TINYINT]                NULL,
  [EmailDigest]          [TINYINT]                NULL,
  [IncludeAttatchments]  [TINYINT]                NULL,
  [AutoSubOnCreate]      [TINYINT]                NULL,
  [AutoSubOnClose]       [TINYINT]                NULL,
  [AutoSubOnEdit]        [TINYINT]                NULL,
  [AutoSubOnAssign]      [TINYINT]                NULL,
  CONSTRAINT [PK_userPreferences] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
  TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[userroles]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[userroles] (
  [ID]           [BIGINT] IDENTITY (1, 1) NOT NULL,
  [userID]       [INT]                    NOT NULL,
  [roleID]       [INT]                    NOT NULL,
  [CreationDate] [DATETIME]               NOT NULL,
  [CreatedBy]    [NVARCHAR](50)           NOT NULL,
  CONSTRAINT [PK_userspermissions] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users] (
  [ID]               [INT] IDENTITY (1, 1) NOT NULL,
  [UserID]           [NVARCHAR](50)        NOT NULL,
  [FirstName]        [NVARCHAR](500)       NOT NULL,
  [LastName]         [NVARCHAR](500)       NULL,
  [Email]            [NVARCHAR](250)       NULL,
  [Password]         [NVARCHAR](256)       NULL,
  [StaffID]          [NVARCHAR](20)        NULL,
  [Enabled]          [TINYINT]             NULL,
  [CreationDate]     [VARCHAR](8000)       NULL,
  [ModificationDate] [VARCHAR](8000)       NULL,
  [CreatedBy]        [NVARCHAR](50)        NULL,
  [ModifiedBy]       [NVARCHAR](50)        NULL,
  [Locale]           [NCHAR](10)           NULL,
  [LoginAttempts]    [INT]                 NULL,
  [SystemUser]       [TINYINT]             NULL,
  [Title]            [NVARCHAR](150)       NULL,
  [Department]       [NVARCHAR](50)        NULL,
  [LDAPUser]         [TINYINT]             NULL,
  CONSTRAINT [PK_users_1] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[usertopic]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[usertopic] (
  [ID]               [INT] IDENTITY (1, 1) NOT NULL,
  [UserID]           [NVARCHAR](50)        NOT NULL,
  [TopicID]          [INT]                 NOT NULL,
  [CreatedBy]        [NVARCHAR](45)        NOT NULL,
  [CreationDate]     [DATETIME]            NOT NULL,
  [ModifiedBy]       [NVARCHAR](45)        NULL,
  [ModificationDate] [DATETIME]            NULL,
  [Status]           [TINYINT]             NOT NULL,
  CONSTRAINT [PK_usertopic] PRIMARY KEY CLUSTERED
    (
      [ID] ASC
    )
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_maincategory_name]    Script Date: 10/23/2018 8:24:54 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_maincategory_name]
  ON [dbo].[maincategory]
  (
    [ArabicLabel] ASC,
    [EnglishLabel] ASC
  )
  WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
  ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_subcategory_name]    Script Date: 10/23/2018 8:24:54 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_subcategory_name]
  ON [dbo].[subcategory]
  (
    [EnglishLabel] ASC,
    [MainCategory] ASC,
    [ArabicLabel] ASC
  )
  WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
  ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [IX_topic_name]    Script Date: 10/23/2018 8:24:54 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_topic_name]
  ON [dbo].[topic]
  (
    [EnglishLabel] ASC,
    [ArabicLabel] ASC,
    [SubCategory] ASC
  )
  WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
  ON [PRIMARY]
GO
/****** Object:  Index [IX_TopicSLA]    Script Date: 10/23/2018 8:24:54 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_TopicSLA]
  ON [dbo].[topicsla]
  (
    [SLAID] ASC,
    [TopicID] ASC,
    [SLALevel] ASC
  )
  WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
  ON [PRIMARY]
GO
/****** Object:  Index [IX_topicspermissions]    Script Date: 10/23/2018 8:24:54 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [IX_topicspermissions]
  ON [dbo].[topicspermissions]
  (
    [assigne] ASC
  )
  WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
  ON [PRIMARY]
GO
ALTER TABLE [dbo].[assignedtickets]
  ADD CONSTRAINT [DF__usersassi__Ticke__6ABAD62E] DEFAULT (NULL) FOR [ticketID]
GO
ALTER TABLE [dbo].[assignedtickets]
  ADD CONSTRAINT [DF__usersassi__Assig__6BAEFA67] DEFAULT (NULL) FOR [userID]
GO
ALTER TABLE [dbo].[assignedtickets]
  ADD CONSTRAINT [DF__usersassi__Statu__67DE6983] DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[assignedtickets]
  ADD CONSTRAINT [DF__usersassi__Creat__66EA454A] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[assignedtickets]
  ADD CONSTRAINT [DF__usersassi__Modif__68D28DBC] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[assignedtickets]
  ADD CONSTRAINT [DF__usersassi__Modif__69C6B1F5] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[attachments]
  ADD CONSTRAINT [DF__attachmen__FileT__4E3E9311] DEFAULT (NULL) FOR [FileType]
GO
ALTER TABLE [dbo].[attachments]
  ADD CONSTRAINT [DF__attachment__Hash__4F32B74A] DEFAULT (NULL) FOR [Hash]
GO
ALTER TABLE [dbo].[attachments]
  ADD CONSTRAINT [DF__attachmen__Creat__5026DB83] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [isWeekday]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [isHoliday]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [Y]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [FY]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [Q]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [M]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [D]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [DW]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [monthname]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [dayname]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [W]
GO
ALTER TABLE [dbo].[calendar]
  ADD DEFAULT (NULL) FOR [Description]
GO
ALTER TABLE [dbo].[customeraccounts]
  ADD CONSTRAINT [DF_CustomerAccounts_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[customeraccounts]
  ADD CONSTRAINT [DF_CustomerAccounts_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[customeraccounts]
  ADD CONSTRAINT [DF_CustomerAccounts_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[customeraccounts]
  ADD CONSTRAINT [DF_CustomerAccounts_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[emailhistory]
  ADD CONSTRAINT [DF__emailhist__Creat__30792600] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[emailhistory]
  ADD CONSTRAINT [DF__emailhist__SendD__316D4A39] DEFAULT (NULL) FOR [SendDate]
GO
ALTER TABLE [dbo].[emailhistory]
  ADD CONSTRAINT [DF__emailhisto__Type__32616E72] DEFAULT (NULL) FOR [Type]
GO
ALTER TABLE [dbo].[emailhistory]
  ADD CONSTRAINT [DF__emailhist__Email__335592AB] DEFAULT (NULL) FOR [EmailMessage]
GO
ALTER TABLE [dbo].[emailtemplates]
  ADD CONSTRAINT [DF__emailtemp__Templ__45BE5BA9] DEFAULT (NULL) FOR [TemplateName]
GO
ALTER TABLE [dbo].[emailtemplates]
  ADD CONSTRAINT [DF__emailtemp__enabl__44CA3770] DEFAULT (NULL) FOR [enabled]
GO
ALTER TABLE [dbo].[emailtemplates]
  ADD CONSTRAINT [DF__emailtemp__Creat__46B27FE2] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[emailtemplates]
  ADD CONSTRAINT [DF__emailtemp__Creat__47A6A41B] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[emailtemplates]
  ADD CONSTRAINT [DF__emailtemp__Modif__489AC854] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[emailtemplates]
  ADD CONSTRAINT [DF__emailtemp__Modif__498EEC8D] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[groups]
  ADD CONSTRAINT [DF__groups__Modifica__4A8310C6] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[groups]
  ADD CONSTRAINT [DF__groups__Modified__4B7734FF] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[groups]
  ADD CONSTRAINT [DF_group_Enabled] DEFAULT ((1)) FOR [Enabled]
GO
ALTER TABLE [dbo].[holdreason]
  ADD CONSTRAINT [DF__tickethol__SortO__3DE82FB7] DEFAULT (NULL) FOR [SortOrder]
GO
ALTER TABLE [dbo].[holdreason]
  ADD CONSTRAINT [DF__tickethol__Arabi__3BFFE745] DEFAULT (NULL) FOR [ArabicLabel]
GO
ALTER TABLE [dbo].[holdreason]
  ADD CONSTRAINT [DF__tickethol__Engli__3CF40B7E] DEFAULT (NULL) FOR [EnglishLabel]
GO
ALTER TABLE [dbo].[holdreason]
  ADD CONSTRAINT [DF_ticketholdreason_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[holdreason]
  ADD CONSTRAINT [DF_ticketholdreason_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[holdreason]
  ADD CONSTRAINT [DF_ticketholdreason_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[holdreason]
  ADD CONSTRAINT [DF_ticketholdreason_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[holidays]
  ADD CONSTRAINT [DF__holidays__Holida__4C6B5938] DEFAULT (NULL) FOR [HolidayName]
GO
ALTER TABLE [dbo].[holidays]
  ADD CONSTRAINT [DF_holidays_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[maincategory]
  ADD CONSTRAINT [DF__maincateg__Creat__08362A7C] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[maincategory]
  ADD CONSTRAINT [DF__maincateg__Updat__092A4EB5] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[maincategory]
  ADD CONSTRAINT [DF__maincateg__Creat__0A1E72EE] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[maincategory]
  ADD CONSTRAINT [DF__maincateg__Modif__0B129727] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[notifications]
  ADD CONSTRAINT [DF_NotificationMessages_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[notifications]
  ADD CONSTRAINT [DF_NotificationMessages_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[sla]
  ADD CONSTRAINT [DF__sla__CreationDat__5669C4BE] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[sla]
  ADD CONSTRAINT [DF__sla__Creator__575DE8F7] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[sla]
  ADD CONSTRAINT [DF__sla__UpdatedBy__58520D30] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[sla]
  ADD CONSTRAINT [DF__sla__UpdatedDate__59463169] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[slausers]
  ADD CONSTRAINT [DF__userssla__UserId__74B941B4] DEFAULT (NULL) FOR [UserId]
GO
ALTER TABLE [dbo].[slausers]
  ADD CONSTRAINT [DF__userssla__Sla__75AD65ED] DEFAULT (NULL) FOR [Sla]
GO
ALTER TABLE [dbo].[slausers]
  ADD CONSTRAINT [DF__userssla__Create__76A18A26] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[slausers]
  ADD CONSTRAINT [DF__userssla__Creati__7795AE5F] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[slausers]
  ADD CONSTRAINT [DF__userssla__Modifi__7889D298] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[slausers]
  ADD CONSTRAINT [DF__userssla__Modifi__797DF6D1] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[smsHistory]
  ADD CONSTRAINT [DF__smshistor__SendD__5C229E14] DEFAULT (NULL) FOR [SendDate]
GO
ALTER TABLE [dbo].[smsHistory]
  ADD CONSTRAINT [DF__smshistor__Sendi__5D16C24D] DEFAULT (NULL) FOR [SendingON]
GO
ALTER TABLE [dbo].[smsTemplate]
  ADD CONSTRAINT [DF__smstempla__enabl__6CD828CA] DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[smsTemplate]
  ADD CONSTRAINT [DF__smstempla__Creat__6DCC4D03] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[smsTemplate]
  ADD CONSTRAINT [DF__smstempla__Creat__6FB49575] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[smsTemplate]
  ADD CONSTRAINT [DF__smstempla__Updat__6EC0713C] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[smsTemplate]
  ADD CONSTRAINT [DF__smstempla__Updat__70A8B9AE] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[sourceChannel]
  ADD CONSTRAINT [DF_sourceChannel_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[sourceChannel]
  ADD CONSTRAINT [DF_sourceChannel_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[sourceChannel]
  ADD CONSTRAINT [DF_sourceChannel_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[sourceChannel]
  ADD CONSTRAINT [DF_sourceChannel_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[status]
  ADD CONSTRAINT [DF__status__OrderBy__719CDDE7] DEFAULT (NULL) FOR [ListOrder]
GO
ALTER TABLE [dbo].[status]
  ADD CONSTRAINT [DF__status__Enabled__72910220] DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[status]
  ADD CONSTRAINT [DF__status__EnableSL__74794A92] DEFAULT (NULL) FOR [EnableSLA]
GO
ALTER TABLE [dbo].[status]
  ADD CONSTRAINT [DF__status__DisplayO__756D6ECB] DEFAULT (NULL) FOR [DisplayOnTicketEdit]
GO
ALTER TABLE [dbo].[status]
  ADD CONSTRAINT [DF_status_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[status]
  ADD CONSTRAINT [DF_status_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[status]
  ADD CONSTRAINT [DF_status_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[status]
  ADD CONSTRAINT [DF_status_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[subcategory]
  ADD CONSTRAINT [DF__subcatego__Creat__7306036C] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[subcategory]
  ADD CONSTRAINT [DF__subcatego__Updat__73FA27A5] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[subcategory]
  ADD CONSTRAINT [DF__subcatego__Creat__74EE4BDE] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[subcategory]
  ADD CONSTRAINT [DF__subcatego__Modif__75E27017] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[subscriptions]
  ADD CONSTRAINT [DF_Subscriptions_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[subscriptions]
  ADD CONSTRAINT [DF_Subscriptions_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[subscriptions]
  ADD CONSTRAINT [DF_Subscriptions_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[subscriptions]
  ADD CONSTRAINT [DF_Subscriptions_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Topic__78BEDCC2] DEFAULT (NULL) FOR [Topic]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Status__79B300FB] DEFAULT (NULL) FOR [CurrentStatus]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Creator__7AA72534] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Modifier__7C8F6DA6] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__UpdateDa__7D8391DF] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__CrossedS__7B9B496D] DEFAULT (NULL) FOR [CrossedMainSLA]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Customer__7E77B618] DEFAULT (NULL) FOR [CustomerAccount]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__SourceCh__033C6B35] DEFAULT (NULL) FOR [SourceChannel]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Title__04308F6E] DEFAULT (NULL) FOR [Subject]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Type__0524B3A7] DEFAULT (NULL) FOR [TicketType]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Assigned__0CC5D56F] DEFAULT (NULL) FOR [AssignedTo]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Escalati__0DB9F9A8] DEFAULT (NULL) FOR [EscalationCalDate]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__LastSLA__0EAE1DE1] DEFAULT (NULL) FOR [LastSLA]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Language__0FA2421A] DEFAULT (NULL) FOR [Language]
GO
ALTER TABLE [dbo].[ticket]
  ADD CONSTRAINT [DF__ticket__Priority__10966653] DEFAULT (NULL) FOR [Priority]
GO
ALTER TABLE [dbo].[ticketactions]
  ADD CONSTRAINT [DF__ticketact__Arabi__2BC97F7C] DEFAULT (NULL) FOR [ArabicLabel]
GO
ALTER TABLE [dbo].[ticketactions]
  ADD CONSTRAINT [DF__ticketact__Engli__2CBDA3B5] DEFAULT (NULL) FOR [EnglishLabel]
GO
ALTER TABLE [dbo].[ticketactions]
  ADD CONSTRAINT [DF__ticketact__Enabl__2DB1C7EE] DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[ticketactions]
  ADD CONSTRAINT [DF_ticketaction_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[ticketactions]
  ADD CONSTRAINT [DF_ticketaction_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[ticketactions]
  ADD CONSTRAINT [DF_ticketaction_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[ticketactions]
  ADD CONSTRAINT [DF_ticketaction_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF_ticketdata_ActionID] DEFAULT (NULL) FOR [ActionID]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF__ticketdat__Creat__2779CBAB] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF__ticketdat__Creat__286DEFE4] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF__ticketdat__Modif__2C3E80C8] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF__ticketdat__Modif__2D32A501] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF_ticketdata_OldStatus] DEFAULT (NULL) FOR [OldStatus]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF_ticketdata_NewStatus] DEFAULT (NULL) FOR [NewStatus]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF_ticketdata_OldTopic] DEFAULT (NULL) FOR [OldTopic]
GO
ALTER TABLE [dbo].[ticketdata]
  ADD CONSTRAINT [DF_ticketdata_NewTopic] DEFAULT (NULL) FOR [NewTopic]
GO
ALTER TABLE [dbo].[topic]
  ADD CONSTRAINT [DF__topic__CreationD__3B80C458] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[topic]
  ADD CONSTRAINT [DF__topic__Modificat__3C74E891] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[topic]
  ADD CONSTRAINT [DF__topic__CreatedBy__3D690CCA] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[topic]
  ADD CONSTRAINT [DF__topic__ModifiedB__3E5D3103] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[topicsla]
  ADD CONSTRAINT [DF_TopicSLA_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[topicsla]
  ADD CONSTRAINT [DF_TopicSLA_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[topicsla]
  ADD CONSTRAINT [DF_TopicSLA_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[topicsla]
  ADD CONSTRAINT [DF_TopicSLA_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[userpreferences]
  ADD CONSTRAINT [DF_userPreferences_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[userpreferences]
  ADD CONSTRAINT [DF_userPreferences_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[userpreferences]
  ADD CONSTRAINT [DF_userPreferences_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[userpreferences]
  ADD CONSTRAINT [DF_userPreferences_DisableEmails] DEFAULT ((0)) FOR [EmailsNotifications]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__Email__4F87BD05] DEFAULT (NULL) FOR [Email]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__StaffID__507BE13E] DEFAULT (NULL) FOR [StaffID]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__Enabled__51700577] DEFAULT (NULL) FOR [Enabled]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__CreationD__5634BA94] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__Modificat__5728DECD] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__CreatedBy__581D0306] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__updatedBy__5911273F] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__Locale__5A054B78] DEFAULT (NULL) FOR [Locale]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__LoginAtte__5AF96FB1] DEFAULT ((3)) FOR [LoginAttempts]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__SystemUse__5BED93EA] DEFAULT ((0)) FOR [SystemUser]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__Title__5DD5DC5C] DEFAULT (NULL) FOR [Title]
GO
ALTER TABLE [dbo].[users]
  ADD CONSTRAINT [DF__users__Departmen__5ECA0095] DEFAULT (NULL) FOR [Department]
GO
ALTER TABLE [dbo].[usertopic]
  ADD CONSTRAINT [DF_usertopic_CreatedBy] DEFAULT (NULL) FOR [CreatedBy]
GO
ALTER TABLE [dbo].[usertopic]
  ADD CONSTRAINT [DF_usertopic_CreationDate] DEFAULT (NULL) FOR [CreationDate]
GO
ALTER TABLE [dbo].[usertopic]
  ADD CONSTRAINT [DF_usertopic_ModifiedBy] DEFAULT (NULL) FOR [ModifiedBy]
GO
ALTER TABLE [dbo].[usertopic]
  ADD CONSTRAINT [DF_usertopic_ModificationDate] DEFAULT (NULL) FOR [ModificationDate]
GO
ALTER TABLE [dbo].[emailhistory]
  WITH NOCHECK ADD CONSTRAINT [FK_EmailHistory_EmailMessage] FOREIGN KEY ([EmailMessage])
REFERENCES [dbo].[emailmessage] ([Id])
  ON UPDATE CASCADE
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[emailhistory]
  CHECK CONSTRAINT [FK_EmailHistory_EmailMessage]
GO
ALTER TABLE [dbo].[escalationhistory]
  WITH NOCHECK ADD CONSTRAINT [FK_EscalationHistory_Ticket] FOREIGN KEY ([TicketID])
REFERENCES [dbo].[ticket] ([ID])
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[escalationhistory]
  CHECK CONSTRAINT [FK_EscalationHistory_Ticket]
GO
ALTER TABLE [dbo].[escalationhistory]
  WITH NOCHECK ADD CONSTRAINT [FK_EsclationHistory_SLA] FOREIGN KEY ([SLA])
REFERENCES [dbo].[sla] ([ID])
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[escalationhistory]
  CHECK CONSTRAINT [FK_EsclationHistory_SLA]
GO
ALTER TABLE [dbo].[maincatholidays]
  WITH NOCHECK ADD CONSTRAINT [FK_MainCatHolidays_Holidays] FOREIGN KEY ([HolidayID])
REFERENCES [dbo].[holidays] ([HolidayID])
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[maincatholidays]
  CHECK CONSTRAINT [FK_MainCatHolidays_Holidays]
GO
ALTER TABLE [dbo].[maincatholidays]
  WITH NOCHECK ADD CONSTRAINT [FK_MainCatHolidays_MainCategory] FOREIGN KEY ([MainCategory])
REFERENCES [dbo].[maincategory] ([ID])
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[maincatholidays]
  CHECK CONSTRAINT [FK_MainCatHolidays_MainCategory]
GO
ALTER TABLE [dbo].[smsHistory]
  WITH NOCHECK ADD CONSTRAINT [FK_SMSHistory_Ticket] FOREIGN KEY ([TicketID])
REFERENCES [dbo].[ticket] ([ID])
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[smsHistory]
  CHECK CONSTRAINT [FK_SMSHistory_Ticket]
GO
ALTER TABLE [dbo].[subcategory]
  WITH NOCHECK ADD CONSTRAINT [FK_SubCategory_MainCategory] FOREIGN KEY ([MainCategory])
REFERENCES [dbo].[maincategory] ([ID])
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[subcategory]
  CHECK CONSTRAINT [FK_SubCategory_MainCategory]
GO
ALTER TABLE [dbo].[ticketdata]
  WITH NOCHECK ADD CONSTRAINT [FK_TicketData_TicketID] FOREIGN KEY ([TicketID])
REFERENCES [dbo].[ticket] ([ID])
  ON UPDATE CASCADE
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ticketdata]
  CHECK CONSTRAINT [FK_TicketData_TicketID]
GO
ALTER TABLE [dbo].[ticketlock]
  WITH NOCHECK ADD CONSTRAINT [FK_Lock_Ticket] FOREIGN KEY ([TicketID])
REFERENCES [dbo].[ticket] ([ID])
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ticketlock]
  CHECK CONSTRAINT [FK_Lock_Ticket]
GO
ALTER TABLE [dbo].[topic]
  WITH NOCHECK ADD CONSTRAINT [FK_Topic_SubCategory] FOREIGN KEY ([SubCategory])
REFERENCES [dbo].[subcategory] ([ID])
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[topic]
  CHECK CONSTRAINT [FK_Topic_SubCategory]
GO
ALTER TABLE [dbo].[topicsla]
  WITH CHECK ADD CONSTRAINT [FK_TopicSLA_sla] FOREIGN KEY ([SLAID])
REFERENCES [dbo].[sla] ([ID])
  ON UPDATE CASCADE
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[topicsla]
  CHECK CONSTRAINT [FK_TopicSLA_sla]
GO
ALTER TABLE [dbo].[topicsla]
  WITH CHECK ADD CONSTRAINT [FK_TopicSLA_topic] FOREIGN KEY ([TopicID])
REFERENCES [dbo].[topic] ([ID])
  ON UPDATE CASCADE
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[topicsla]
  CHECK CONSTRAINT [FK_TopicSLA_topic]
GO
ALTER TABLE [dbo].[topicspermissions]
  WITH CHECK ADD CONSTRAINT [FK_TopicsPermissions_topic] FOREIGN KEY ([topicId])
REFERENCES [dbo].[topic] ([ID])
  ON UPDATE CASCADE
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[topicspermissions]
  CHECK CONSTRAINT [FK_TopicsPermissions_topic]
GO
ALTER TABLE [dbo].[userroles]
  WITH CHECK ADD CONSTRAINT [FK_userspermissions_userspermissions] FOREIGN KEY ([ID])
REFERENCES [dbo].[userroles] ([ID])
GO
ALTER TABLE [dbo].[userroles]
  CHECK CONSTRAINT [FK_userspermissions_userspermissions]
GO
ALTER TABLE [dbo].[usertopic]
  WITH NOCHECK ADD CONSTRAINT [FK_UsersTopic_Topic] FOREIGN KEY ([TopicID])
REFERENCES [dbo].[topic] ([ID])
  ON UPDATE CASCADE
  ON DELETE CASCADE
GO
ALTER TABLE [dbo].[usertopic]
  CHECK CONSTRAINT [FK_UsersTopic_Topic]
GO
/****** Object:  StoredProcedure [dbo].[GetUserMainCats]    Script Date: 10/23/2018 8:24:54 AM ******/
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
    @userId INT
AS
  BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    SELECT DISTINCT *
    FROM
      (SELECT DISTINCT mc.*
       FROM dbo.topicspermissions tp LEFT JOIN topic t ON tp.topicId = t.ID
         LEFT JOIN subcategory s ON s.ID = t.SubCategory
         LEFT JOIN maincategory mc ON mc.ID = s.MainCategory
       WHERE t.Enabled = 1 AND mc.Enabled = 1 AND s.Enabled = 1 AND tp.assigne = @userId AND tp.type = 'user'
       UNION ALL
       SELECT DISTINCT mc.*
       FROM dbo.topicspermissions tp LEFT JOIN topic t ON tp.topicId = t.ID
         LEFT JOIN subcategory s ON s.ID = t.SubCategory
         LEFT JOIN maincategory mc ON mc.ID = s.MainCategory
       WHERE t.Enabled = 1 AND mc.Enabled = 1 AND s.Enabled = 1 AND tp.type = 'group' AND tp.assigne IN (SELECT groupID
                                                                                                         FROM usergroups
                                                                                                         WHERE userID =
                                                                                                               @userId)
      ) topics
    WHERE topics.ID IS NOT NULL
  END
GO
/****** Object:  StoredProcedure [dbo].[GetUserPermissions]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GetUserPermissions]
  -- Add the parameters for the stored procedure here
    @userId INT
AS
  BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    SELECT DISTINCT *
    FROM
      (SELECT DISTINCT p.*
       FROM dbo.permissions p RIGHT JOIN [dbo].[rolepermissions] rp ON p.id = rp.permissionID
                                                                       AND rp.roleID IN (SELECT roleID
                                                                                         FROM [userroles]
                                                                                           LEFT JOIN users ON users.ID =
                                                                                                              [userroles].userID
                                                                                         WHERE users.ID = @userId)
       UNION ALL
       SELECT DISTINCT p.*
       FROM dbo.permissions p RIGHT JOIN [dbo].[rolepermissions] rp ON p.id = rp.permissionID
                                                                       AND rp.roleID IN (SELECT roleID
                                                                                         FROM [dbo].[grouproles]
                                                                                           LEFT JOIN dbo.[groups]
                                                                                             ON dbo.[groups].ID =
                                                                                                [grouproles].groupID
                                                                                         WHERE groups.Enabled = 1 AND
                                                                                               groups.id IN
                                                                                               (SELECT groupID
                                                                                                FROM usergroups
                                                                                                WHERE userID = @userId))
      ) Perms
    WHERE Perms.ID IS NOT NULL
  END
GO
/****** Object:  StoredProcedure [dbo].[GetUserSubCats]    Script Date: 10/23/2018 8:24:54 AM ******/
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
    @userId  INT,
    @mainCat INT
AS
  BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    SELECT DISTINCT *
    FROM
      (SELECT DISTINCT s.*
       FROM dbo.topicspermissions tp LEFT JOIN topic t ON tp.topicId = t.ID
         LEFT JOIN subcategory s ON s.ID = t.SubCategory
       WHERE s.MainCategory = @mainCat AND t.Enabled = 1 AND s.Enabled = 1 AND tp.assigne = @userId AND tp.type = 'user'
       UNION ALL
       SELECT DISTINCT s.*
       FROM dbo.topicspermissions tp LEFT JOIN topic t ON tp.topicId = t.ID
         LEFT JOIN subcategory s ON s.ID = t.SubCategory
       WHERE s.MainCategory = @mainCat AND t.Enabled = 1 AND s.Enabled = 1 AND tp.type = 'group' AND
             tp.assigne IN (SELECT groupID
                            FROM usergroups
                            WHERE userID = @userId)
      ) topics
    WHERE topics.ID IS NOT NULL
  END
GO
/****** Object:  StoredProcedure [dbo].[GetUserTopicPermissions]    Script Date: 10/23/2018 8:24:54 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:      <Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GetUserTopicPermissions]
  -- Add the parameters for the stored procedure here
    @userId  INT,
    @topicID INT
AS
  BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;
    IF @topicID <> -1
      BEGIN
        SELECT DISTINCT *
        FROM (SELECT DISTINCT tp.*
              FROM dbo.topicspermissions tp
              WHERE tp.assigne = @userId
                    AND tp.type = 'user'
              UNION ALL
              SELECT DISTINCT tp.*
              FROM dbo.topicspermissions tp
              WHERE tp.type = 'group'
                    AND tp.assigne IN (SELECT groupID
                                       FROM usergroups
                                       WHERE userID = @userId)) Perms
        WHERE Perms.ID IS NOT NULL
      END
    ELSE
      BEGIN
        SELECT DISTINCT *
        FROM (SELECT DISTINCT tp.*
              FROM dbo.topicspermissions tp
              WHERE tp.topicId = @topicID
                    AND tp.assigne = @userId
                    AND tp.type = 'user'
              UNION ALL
              SELECT DISTINCT tp.*
              FROM dbo.topicspermissions tp
              WHERE tp.type = 'group'
                    AND tp.topicId = @topicID
                    AND tp.assigne IN (SELECT groupID
                                       FROM usergroups
                                       WHERE userID = @userId)) Perms
        WHERE Perms.ID IS NOT NULL
      END

  END
GO
/****** Object:  StoredProcedure [dbo].[GetUserTopics]    Script Date: 10/23/2018 8:24:54 AM ******/
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
    @userId INT
AS
  BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    SELECT DISTINCT *
    FROM
      (SELECT DISTINCT t.*
       FROM dbo.topicspermissions tp LEFT JOIN topic t ON tp.topicId = t.ID AND t.Enabled = 1
       WHERE tp.assigne = @userId AND tp.type = 'user'
       UNION ALL
       SELECT DISTINCT t.*
       FROM dbo.topicspermissions tp LEFT JOIN topic t ON tp.topicId = t.ID AND t.Enabled = 1
       WHERE tp.type = 'group' AND tp.assigne IN (SELECT groupID
                                                  FROM usergroups
                                                  WHERE userID = @userId)
      ) topics
    WHERE topics.ID IS NOT NULL
  END
GO
/****** Object:  StoredProcedure [dbo].[GetUserTopicsBySubCat]    Script Date: 10/23/2018 8:24:54 AM ******/
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
    @userId INT,
    @subCat INT
AS
  BEGIN
    -- SET NOCOUNT ON added to prevent extra result sets from
    -- interfering with SELECT statements.
    SET NOCOUNT ON;

    SELECT DISTINCT *
    FROM
      (SELECT DISTINCT t.*
       FROM dbo.topicspermissions tp LEFT JOIN topic t ON tp.topicId = t.ID
       WHERE t.SubCategory = @subCat AND t.Enabled = 1 AND tp.assigne = @userId AND tp.type = 'user'
       UNION ALL
       SELECT DISTINCT t.*
       FROM dbo.topicspermissions tp LEFT JOIN topic t ON tp.topicId = t.ID
       WHERE t.SubCategory = @subCat AND t.Enabled = 1 AND tp.type = 'group' AND tp.assigne IN (SELECT groupID
                                                                                                FROM usergroups
                                                                                                WHERE userID = @userId)
      ) topics
    WHERE topics.ID IS NOT NULL
  END
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.usersassignedtickets',
                                @level0type = N'SCHEMA', @level0name = N'dbo', @level1type = N'TABLE',
                                @level1name = N'assignedtickets'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.attachments', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'attachments'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.calendar', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'calendar'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.emailhistory', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'emailhistory'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.emailmessage', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'emailmessage'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.emailtemplates',
                                @level0type = N'SCHEMA', @level0name = N'dbo', @level1type = N'TABLE',
                                @level1name = N'emailtemplates'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.escalationhistory',
                                @level0type = N'SCHEMA', @level0name = N'dbo', @level1type = N'TABLE',
                                @level1name = N'escalationhistory'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.globalconfiguration',
                                @level0type = N'SCHEMA', @level0name = N'dbo', @level1type = N'TABLE',
                                @level1name = N'globalconfiguration'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.group', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'groups'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.ticketholdreason',
                                @level0type = N'SCHEMA', @level0name = N'dbo', @level1type = N'TABLE',
                                @level1name = N'holdreason'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.holidays', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'holidays'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.logindetails', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'logindetails'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.maincategory', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'maincategory'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.maincatholidays',
                                @level0type = N'SCHEMA', @level0name = N'dbo', @level1type = N'TABLE',
                                @level1name = N'maincatholidays'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.sla', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'sla'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.userssla', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'slausers'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.smshistory', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'smsHistory'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.smstemplate', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'smsTemplate'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.sourcechannel', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'sourceChannel'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.status', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'status'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.subcategory', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'subcategory'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.ticket', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'ticket'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.ticketactions', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'ticketactions'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.ticketdata', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'ticketdata'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.ticketlock', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'ticketlock'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.type', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'tickettypes'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.topic', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'topic'
GO
EXEC sys.sp_addextendedproperty @name = N'MS_SSMA_SOURCE', @value = N'ticketing.users', @level0type = N'SCHEMA',
                                @level0name = N'dbo', @level1type = N'TABLE', @level1name = N'users'
GO
USE [master]
GO
ALTER DATABASE [BAJTicketingNew] SET READ_WRITE
GO
