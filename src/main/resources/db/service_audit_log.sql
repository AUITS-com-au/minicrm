USE [BAJTicketingNew]
GO

/****** Object:  Table [dbo].[ServiceAuditLog]    Script Date: 4/2/2019 6:28:02 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ServiceAuditLog](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[DateTime] [datetime] NOT NULL,
	[UserID] [nvarchar](50) NOT NULL,
	[ServiceName] [nvarchar](50) NULL,
	[CustomerID] [nvarchar](20) NULL,
	[CreditCardNo] [nvarchar](20) NULL,
	[AccountNo] [nvarchar](20) NULL,
	[ExtField1] [nvarchar](250) NULL,
	[ExtField2] [nvarchar](250) NULL,
	[ExtField3] [nvarchar](250) NULL,
	[ExtField4] [nvarchar](250) NULL,
	[ExtField5] [nvarchar](250) NULL,
	[IPAddress] [nvarchar](50) NULL,
	[StatusCode] [nvarchar](10) NULL,
 CONSTRAINT [PK_ServiceAuditLog] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

