#pragma once


// MyProPage2 dialog

class MyProPage2 : public CPropertyPage
{
	DECLARE_DYNAMIC(MyProPage2)

public:
	MyProPage2();
	virtual ~MyProPage2();

// Dialog Data
	enum { IDD = IDD_MYPROPAGE2 };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	DECLARE_MESSAGE_MAP()
};
