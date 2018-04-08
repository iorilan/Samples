// mfcLab1Doc.cpp : implementation of the CmfcLab1Doc class
//

#include "stdafx.h"
#include "mfcLab1.h"

#include "mfcLab1Doc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CmfcLab1Doc

IMPLEMENT_DYNCREATE(CmfcLab1Doc, CDocument)

BEGIN_MESSAGE_MAP(CmfcLab1Doc, CDocument)
END_MESSAGE_MAP()


// CmfcLab1Doc construction/destruction

CmfcLab1Doc::CmfcLab1Doc()
{
	// TODO: add one-time construction code here

}

CmfcLab1Doc::~CmfcLab1Doc()
{
}

BOOL CmfcLab1Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}




// CmfcLab1Doc serialization

void CmfcLab1Doc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: add storing code here
	}
	else
	{
		// TODO: add loading code here
	}
}


// CmfcLab1Doc diagnostics

#ifdef _DEBUG
void CmfcLab1Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void CmfcLab1Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// CmfcLab1Doc commands
