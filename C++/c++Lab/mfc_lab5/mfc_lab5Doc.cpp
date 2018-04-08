// mfc_lab5Doc.cpp : implementation of the Cmfc_lab5Doc class
//

#include "stdafx.h"
#include "mfc_lab5.h"

#include "mfc_lab5Doc.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// Cmfc_lab5Doc

IMPLEMENT_DYNCREATE(Cmfc_lab5Doc, CDocument)

BEGIN_MESSAGE_MAP(Cmfc_lab5Doc, CDocument)
END_MESSAGE_MAP()


// Cmfc_lab5Doc construction/destruction

Cmfc_lab5Doc::Cmfc_lab5Doc()
{
	// TODO: add one-time construction code here

}

Cmfc_lab5Doc::~Cmfc_lab5Doc()
{
}

BOOL Cmfc_lab5Doc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: add reinitialization code here
	// (SDI documents will reuse this document)

	return TRUE;
}




// Cmfc_lab5Doc serialization

void Cmfc_lab5Doc::Serialize(CArchive& ar)
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


// Cmfc_lab5Doc diagnostics

#ifdef _DEBUG
void Cmfc_lab5Doc::AssertValid() const
{
	CDocument::AssertValid();
}

void Cmfc_lab5Doc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// Cmfc_lab5Doc commands
