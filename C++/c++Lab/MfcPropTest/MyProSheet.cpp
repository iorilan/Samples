// MyProSheet.cpp : implementation file
//

#include "stdafx.h"
#include "MfcPropTest.h"
#include "MyProSheet.h"


// MyProSheet

IMPLEMENT_DYNAMIC(MyProSheet, CPropertySheet)

MyProSheet::MyProSheet(UINT nIDCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(nIDCaption, pParentWnd, iSelectPage)
{

}

MyProSheet::MyProSheet(LPCTSTR pszCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(pszCaption, pParentWnd, iSelectPage)
{
this->AddPage(&m_prop1);this->AddPage(&m_prop2);this->AddPage(&m_prop3);

}

MyProSheet::~MyProSheet()
{
}


BEGIN_MESSAGE_MAP(MyProSheet, CPropertySheet)
END_MESSAGE_MAP()


// MyProSheet message handlers
