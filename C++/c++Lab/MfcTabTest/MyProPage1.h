#pragma once


// MyProPage1 dialog

class MyProPage1 : public CPropertyPage
{
	DECLARE_DYNAMIC(MyProPage1)

public:
	MyProPage1();
	virtual ~MyProPage1();

// Dialog Data
	enum { IDD = IDD_MYPROPAGE1 };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
