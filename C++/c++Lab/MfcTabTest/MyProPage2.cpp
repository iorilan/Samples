// MyProPage2.cpp : implementation file
//

#include "stdafx.h"
#include "MfcTabTest.h"
#include "MyProPage2.h"


// MyProPage2 dialog

IMPLEMENT_DYNAMIC(MyProPage2, CPropertyPage)

MyProPage2::MyProPage2()
	: CPropertyPage(MyProPage2::IDD)
{

}

MyProPage2::~MyProPage2()
{
}

void MyProPage2::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(MyProPage2, CPropertyPage)
END_MESSAGE_MAP()


// MyProPage2 message handlers
