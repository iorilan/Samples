// MyProPage.cpp : implementation file
//

#include "stdafx.h"
#include "MfcTabTest.h"
#include "MyProPage.h"


// MyProPage

IMPLEMENT_DYNAMIC(MyProPage, CPropertySheet)

MyProPage::MyProPage(UINT nIDCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(nIDCaption, pParentWnd, iSelectPage)
{

}

MyProPage::MyProPage(LPCTSTR pszCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(pszCaption, pParentWnd, iSelectPage)
{

}

MyProPage::~MyProPage()
{
}


BEGIN_MESSAGE_MAP(MyProPage, CPropertySheet)
END_MESSAGE_MAP()


// MyProPage message handlers
