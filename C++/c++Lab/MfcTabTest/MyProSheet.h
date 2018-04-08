#pragma once



// MyProSheet

class MyProSheet : public CPropertySheet
{
	DECLARE_DYNAMIC(MyProSheet)

public:
	MyProSheet(UINT nIDCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	MyProSheet(LPCTSTR pszCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	virtual ~MyProSheet();

protected:
	DECLARE_MESSAGE_MAP()
};


