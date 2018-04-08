#pragma once



// MyCProp

class MyCProp : public CPropertySheet
{
	DECLARE_DYNAMIC(MyCProp)

public:
	MyCProp(UINT nIDCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	MyCProp(LPCTSTR pszCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	virtual ~MyCProp();

protected:
	DECLARE_MESSAGE_MAP()
};


