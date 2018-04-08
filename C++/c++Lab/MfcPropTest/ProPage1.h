
#pragma once


// ProPage1 dialog

class ProPage1 : public CPropertyPage
{
	DECLARE_DYNAMIC(ProPage1)

public:
	ProPage1();
	virtual ~ProPage1();
virtual BOOL ProPage1::OnSetActive();
// Dialog Data
	enum { IDD = IDD_PROPPAGE_LARGE };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
