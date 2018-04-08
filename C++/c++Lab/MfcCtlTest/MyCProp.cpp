// MyCProp.cpp : implementation file
//

#include "stdafx.h"
#include "MfcCtlTest.h"
#include "MyCProp.h"


// MyCProp

IMPLEMENT_DYNAMIC(MyCProp, CPropertySheet)

MyCProp::MyCProp(UINT nIDCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(nIDCaption, pParentWnd, iSelectPage)
{

}

MyCProp::MyCProp(LPCTSTR pszCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(pszCaption, pParentWnd, iSelectPage)
{

}

MyCProp::~MyCProp()
{
}


BEGIN_MESSAGE_MAP(MyCProp, CPropertySheet)
END_MESSAGE_MAP()


// MyCProp message handlers
