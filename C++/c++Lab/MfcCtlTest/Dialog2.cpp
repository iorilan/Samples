// Dialog1.cpp : implementation file
//

#include "stdafx.h"
#include "MfcCtlTest.h"
#include "Dialog2.h"


// CDialog1 dialog

IMPLEMENT_DYNAMIC(CDialog2, CDialog)

CDialog2::CDialog2(CWnd* pParent /*=NULL*/)
	: CDialog(CDialog2::IDD, pParent)
{

	m_nScrollPos = 0;
}

CDialog2::~CDialog2()
{
}

void CDialog2::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CDialog2, CDialog)
	ON_BN_CLICKED(IDOK, &CDialog2::OnBnClickedOk)
	ON_WM_VKEYTOITEM()
	ON_WM_VSCROLL()
END_MESSAGE_MAP()


// CDialog1 message handlers

void CDialog2::OnBnClickedOk()
{
	// TODO: Add your control notification handler code here
	OnOK();
}

int CDialog2::OnVKeyToItem(UINT nKey, CListBox* pListBox, UINT nIndex)
{
	// TODO: Add your message handler code here and/or call default

	return CDialog::OnVKeyToItem(nKey, pListBox, nIndex);
}

void CDialog2::OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{

	int   nDelta; 
	int	  nMaxPos = 500;

  switch   (nSBCode)  
  {  
  case   SB_LINEDOWN:  
  if   (m_nScrollPos   >=   nMaxPos)  
  return;  
  nDelta   =   min(nMaxPos/100,nMaxPos-m_nScrollPos);  
  break;  
   
  case   SB_LINEUP:  
  if   (m_nScrollPos   <=   0)  
  return;  
  nDelta   =   -min(nMaxPos/100,m_nScrollPos);  
  break;  
   
                    case   SB_PAGEDOWN:  
  if   (m_nScrollPos   >=   nMaxPos)  
  return;  
  nDelta   =   min(nMaxPos/10,nMaxPos-m_nScrollPos);  
  break;  
   
  case   SB_THUMBPOSITION:  
  nDelta   =   (int)nPos   -   m_nScrollPos;  
  break;  
   
  case   SB_PAGEUP:  
  if   (m_nScrollPos   <=   0)  
  return;  
  nDelta   =   -min(nMaxPos/10,m_nScrollPos);  
  break;  
   
                    default:  
  return;  
  }

  m_nScrollPos   +=   nDelta;  
  SetScrollPos(SB_VERT,m_nScrollPos,TRUE);  
  ScrollWindow(0,-nDelta);  
  CDialog::OnVScroll(nSBCode,   nPos,   pScrollBar);  

	// TODO: Add your message handler code here and/or call default

	CDialog::OnVScroll(nSBCode, nPos, pScrollBar);
}
