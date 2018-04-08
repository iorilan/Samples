
#pragma once


// ProPage2 dialog

class ProPage2 : public CPropertyPage
{
	DECLARE_DYNAMIC(ProPage2)

public:
	ProPage2();
	virtual ~ProPage2();
	virtual BOOL ProPage2::OnSetActive();

// Dialog Data
	enum { IDD = IDD_PROPPAGE_LARGE1 };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
