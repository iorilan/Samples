// mfc_lab5.h : main header file for the mfc_lab5 application
//
#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"       // main symbols


// Cmfc_lab5App:
// See mfc_lab5.cpp for the implementation of this class
//

class Cmfc_lab5App : public CWinApp
{
public:
	Cmfc_lab5App();


// Overrides
public:
	virtual BOOL InitInstance();

// Implementation
	afx_msg void OnAppAbout();
	DECLARE_MESSAGE_MAP()
};

extern Cmfc_lab5App theApp;