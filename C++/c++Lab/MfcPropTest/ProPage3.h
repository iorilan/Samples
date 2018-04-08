
#pragma once


// ProPage3 dialog

class ProPage3 : public CPropertyPage
{
	DECLARE_DYNAMIC(ProPage3)

public:
	ProPage3();
	virtual ~ProPage3();
	virtual BOOL ProPage3::OnSetActive();

// Dialog Data
	enum { IDD = IDD_PROPPAGE_LARGE2 };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
