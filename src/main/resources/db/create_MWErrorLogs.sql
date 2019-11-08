USE [BAJTicketingNew]
GO

/****** Object:  Table [dbo].[MW_Error_Logs]    Script Date: 18/03/2019 2:32:43 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[MW_Error_Logs](
	[ErrorID] [bigint] IDENTITY(1,1) NOT NULL,
	[ServiceName] [varchar](150) NULL,
	[WSDL] [text] NULL,
	[BasicNumber] [varchar](10) NULL,
	[IDNumber] [varchar](14) NULL,
	[ClientError] [varchar](50) NULL,
	[ClientException] [varchar](50) NULL,
	[NativeStatus] [varchar](20) NULL,
	[NativeState] [text] NULL,
	[NativeErrorDec] [text] NULL,
	[NativeErrorExt] [text] NULL,
	[MessageID] [varchar](50) NULL,
	[RQUID] [varchar](50) NULL,
	[DateTime] [datetime] NULL,
	[Server] [varchar](25) NULL,
 CONSTRAINT [PK_MW_Error_Logs] PRIMARY KEY CLUSTERED 
(
	[ErrorID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

