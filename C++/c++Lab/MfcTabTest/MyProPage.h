#pragma once



// MyProPage

class MyProPage : public CPropertySheet
{
	DECLARE_DYNAMIC(MyProPage)

public:
	MyProPage(UINT nIDCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	MyProPage(LPCTSTR pszCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	virtual ~MyProPage();

	////member

protected:
	DECLARE_MESSAGE_MAP()
};


