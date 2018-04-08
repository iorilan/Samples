// MyProPage1.cpp : implementation file
//

#include "stdafx.h"
#include "MfcTabTest.h"
#include "MyProPage1.h"


// MyProPage1 dialog

IMPLEMENT_DYNAMIC(MyProPage1, CPropertyPage)

MyProPage1::MyProPage1()
	: CPropertyPage(MyProPage1::IDD)
{

}

MyProPage1::~MyProPage1()
{
}

void MyProPage1::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(MyProPage1, CPropertyPage)
END_MESSAGE_MAP()


// MyProPage1 message handlers
