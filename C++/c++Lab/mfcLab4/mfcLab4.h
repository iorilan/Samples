// mfcLab4.h : main header file for the PROJECT_NAME application
//

#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#include "resource.h"		// main symbols


// CmfcLab4App:
// See mfcLab4.cpp for the implementation of this class
//

class CmfcLab4App : public CWinApp
{
public:
	CmfcLab4App();

// Overrides
	public:
	virtual BOOL InitInstance();

// Implementation

	DECLARE_MESSAGE_MAP()
};

extern CmfcLab4App theApp;