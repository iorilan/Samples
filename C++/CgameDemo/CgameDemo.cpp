// CgameDemo.cpp : 定义应用程序的入口点。
//

#include "stdafx.h"
#include "CgameDemo.h"
#include <ddraw.h>
#include <iostream>
#define MAX_LOADSTRING 100


// 全局变量:
HINSTANCE hInst;								// 当前实例
TCHAR szTitle[MAX_LOADSTRING];					// 标题栏文本
TCHAR szWindowClass[MAX_LOADSTRING];			// 主窗口类名

// 此代码模块中包含的函数的前向声明:
ATOM				MyRegisterClass(HINSTANCE hInstance);
BOOL				InitInstance(HINSTANCE, int);
LRESULT CALLBACK	WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK	About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY _tWinMain(HINSTANCE hInstance,
                     HINSTANCE hPrevInstance,
                     LPTSTR    lpCmdLine,
                     int       nCmdShow)
{
	UNREFERENCED_PARAMETER(hPrevInstance);
	UNREFERENCED_PARAMETER(lpCmdLine);

 	// TODO: 在此放置代码。
	MSG msg;
	HACCEL hAccelTable;

	// 初始化全局字符串
	LoadString(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
	LoadString(hInstance, IDC_CGAMEDEMO, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass(hInstance);

	// 执行应用程序初始化:
	if (!InitInstance (hInstance, nCmdShow))
	{
		return FALSE;
	}

	hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_CGAMEDEMO));

	// 主消息循环:
	while (GetMessage(&msg, NULL, 0, 0))
	{
		if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}

	return (int) msg.wParam;
}



//
//  函数: MyRegisterClass()
//
//  目的: 注册窗口类。
//
//  注释:
//
//    仅当希望
//    此代码与添加到 Windows 95 中的“RegisterClassEx”
//    函数之前的 Win32 系统兼容时，才需要此函数及其用法。调用此函数十分重要，
//    这样应用程序就可以获得关联的
//    “格式正确的”小图标。
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
	WNDCLASSEX wcex;

	wcex.cbSize = sizeof(WNDCLASSEX);
	wcex.style			= CS_HREDRAW | CS_VREDRAW;////窗口的风格
	wcex.lpfnWndProc	= WndProc;////窗口消息处理函数的指针
	wcex.cbClsExtra		= 0;////分配给窗口类结构之后的额外字节数
	wcex.cbWndExtra		= 0;////分配给窗口实例之后的额外字节数
	wcex.hInstance		= hInstance;////窗口所对应的应用程序句柄
	wcex.hIcon			= LoadIcon(hInstance, MAKEINTRESOURCE(IDI_CGAMEDEMO));////窗口的坐标
	wcex.hCursor		= LoadCursor(NULL, IDC_ARROW);////窗口的鼠标
	wcex.hbrBackground	= CreateSolidBrush(RGB(100,0,0));//(HBRUSH)(COLOR_WINDOW-1);////窗口的背景
	wcex.lpszMenuName	= NULL,//MAKEINTRESOURCE(IDC_CGAMEDEMO);////窗口的菜单资源名称
	wcex.lpszClassName	= szWindowClass;////窗口类的名称
	wcex.hIconSm		= LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

	return RegisterClassEx(&wcex);////注册窗口
}

//
//   函数: InitInstance(HINSTANCE, int)
//
//   目的: 保存实例句柄并创建主窗口
//
//   注释:
//
//        在此函数中，我们在全局变量中保存实例句柄并
//        创建和显示主程序窗口。
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   HWND hWnd;

   hInst = hInstance; // 将实例句柄存储在全局变量中

   hWnd = CreateWindow(
	   szWindowClass,////创建窗口所用的窗口类的名称
	  NULL,// szTitle, ////窗口标题
	   WS_POPUP | WS_MAXIMIZE,//WS_OVERLAPPEDWINDOW,////窗口风格，定义为普通型
      0,////窗口位置的X坐标
	  0,////窗口位置的Y坐标
	  1000,////窗口的宽度
	  1000, ////窗口的高度
	  NULL,////父窗口句柄
	  NULL, ////菜单句柄
	  hInstance,////应用程序句柄
	  NULL);////一般为NULL

   if (!hWnd)
   {
      return FALSE;
   }
   ////告诉系统显示窗口
   ShowWindow(hWnd, nCmdShow);

   ////立即显示窗口
   UpdateWindow(hWnd);

   return TRUE;
}

//
//  函数: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  目的: 处理主窗口的消息。
//
//  WM_COMMAND	- 处理应用程序菜单
//  WM_PAINT	- 绘制主窗口
//  WM_DESTROY	- 发送退出消息并返回
//
//

int a ,b;

		char ac[25];
		char bc[25];
		char ret[50];
		

LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	int wmId, wmEvent;
	PAINTSTRUCT ps;
	HDC hdc;

	////声明字符串
	std::wstring strShow(_T("Test String"));
	RECT rcText;
	rcText.left = 10;
	rcText.right=100;
	rcText.top = 20;
	rcText.bottom=300;

	switch (message)
	{
	
 	case WM_KEYDOWN:
		switch(wParam){
	case VK_SPACE:
		//MessageBox(hWnd,_T("按下了空格键"),_T("test"),MB_OK);
		////显示字符串

		hdc = BeginPaint(hWnd, &ps);

		HBRUSH brush = CreateSolidBrush(RGB(66,66,66));
		FillRect(hdc,&rcText,brush);
		DeleteObject(brush);////记得及时释放内存

	    DrawText(hdc,strShow.c_str(),(int)strShow.length(),
			&rcText,DT_SINGLELINE|DT_LEFT|DT_END_ELLIPSIS);


		// TODO: 在此添加任意绘图代码...
		EndPaint(hWnd, &ps);

		
		break;
		}
		break;

	////右键按下
	case WM_RBUTTONDOWN:
		
		a = LOWORD(lParam);
		b = HIWORD(lParam);
		itoa(a,ac,10);
	    itoa(b,bc,10);

		strcat(ret,ac);
		strcat(ret,",");
		strcat(ret,bc);

		MessageBoxA(hWnd,ret,"test",MB_OK);
		strcpy(ret,"");

		break;
	////左键按下
	case WM_LBUTTONDOWN:

		

		//MessageBoxA(hWnd,"按下了左键","test",MB_YESNO|MB_HELP|MB_ICONERROR);
	//	ShowWindow(hWnd,SW_SHOWMAXIMIZED);
		break;


	case WM_COMMAND:
		wmId    = LOWORD(wParam);
		wmEvent = HIWORD(wParam);
		// 分析菜单选择:
		switch (wmId)
		{
		case IDM_ABOUT:
			DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
			break;
		case IDM_EXIT:
			DestroyWindow(hWnd);
			break;
		default:
			return DefWindowProc(hWnd, message, wParam, lParam);
		}
		break;
	case WM_PAINT:
		
		break;
	case WM_DESTROY:
		PostQuitMessage(0);
		break;
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
	return 0;
}



///////////////////////
// “关于”框的消息处理程序。
///////////////////////
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
	case WM_INITDIALOG:
		return (INT_PTR)TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
		{
			EndDialog(hDlg, LOWORD(wParam));
			return (INT_PTR)TRUE;
		}
		break;
	}
	return (INT_PTR)FALSE;
}
