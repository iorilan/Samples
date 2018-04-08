// mfc7.h : main header file for the mfc7 application
//
#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"       // main symbols


// Cmfc7App:
// See mfc7.cpp for the implementation of this class
//

class Cmfc7App : public CWinApp
{
public:
	Cmfc7App();


// Overrides
public:
	virtual BOOL InitInstance();

// Implementation
	afx_msg void OnAppAbout();
	DECLARE_MESSAGE_MAP()
};

extern Cmfc7App theApp;