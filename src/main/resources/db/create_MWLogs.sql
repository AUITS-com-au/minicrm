USE [BAJTicketingNew]
GO

/****** Object:  Table [dbo].[MW_Logs]    Script Date: 18/03/2019 2:33:05 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[MW_Logs](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[DateTime] [datetime] NOT NULL,
	[CustomerNumber] [varchar](10) NULL,
	[ServerIP] [varchar](50) NULL,
	[RequestUUID] [varchar](100) NULL,
	[TokenKey] [varchar](50) NULL,
	[cuid] [varchar](50) NULL,
	[CustomerOfficialID] [varchar](15) NULL,
	[ReqServiceName] [varchar](50) NULL,
	[FullRequest] [text] NOT NULL,
	[ResServiceName] [varchar](50) NULL,
	[FullResponse] [text] NULL,
	[WsdlFile] [varchar](250) NULL,
 CONSTRAINT [PK_MWLogs] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

