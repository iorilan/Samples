// mfcLab2.h : main header file for the mfcLab2 application
//
#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"       // main symbols


// CmfcLab2App:
// See mfcLab2.cpp for the implementation of this class
//

class CmfcLab2App : public CWinApp
{
public:
	CmfcLab2App();


// Overrides
public:
	virtual BOOL InitInstance();

// Implementation
	afx_msg void OnAppAbout();
	DECLARE_MESSAGE_MAP()
};

extern CmfcLab2App theApp;