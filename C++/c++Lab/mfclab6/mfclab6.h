// mfclab6.h : main header file for the mfclab6 application
//
#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"       // main symbols


// Cmfclab6App:
// See mfclab6.cpp for the implementation of this class
//

class Cmfclab6App : public CWinApp
{
public:
	Cmfclab6App();


// Overrides
public:
	virtual BOOL InitInstance();

// Implementation
	afx_msg void OnAppAbout();
	DECLARE_MESSAGE_MAP()
};

extern Cmfclab6App theApp;