#pragma once
#include "ProPage1.h"
#include "ProPage2.h"
#include "ProPage3.h"


// MyProSheet

class MyProSheet : public CPropertySheet
{
	DECLARE_DYNAMIC(MyProSheet)

public:
	MyProSheet(UINT nIDCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	MyProSheet(LPCTSTR pszCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	virtual ~MyProSheet();
////members
ProPage1 m_prop1;
ProPage2 m_prop2;
ProPage3 m_prop3;
protected:
	DECLARE_MESSAGE_MAP()
};


